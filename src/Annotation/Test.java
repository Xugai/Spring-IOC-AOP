package Annotation;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Test {
	
	public static String query(Filter F){
		//1、先获取表名，通过类的注解获取
		Class c = F.getClass();
		//判断是否有注解，若有，则获得该注解值
		@SuppressWarnings("unchecked")
		boolean exist = c.isAnnotationPresent(Table.class);
		if(!exist){
			return null;
		}
		@SuppressWarnings("unchecked")
		Table t = (Table)c.getAnnotation(Table.class);
		String tableName = t.value();
		
		String sql = "select * from "+tableName+" where ";
		
		//接下来获取表中的字段名
		Field [] fs = c.getDeclaredFields();
		
		for (Field field : fs) {
			//先通过反射获取每个数据成员的注解值，也即是SQL表的字段名
			boolean isExist = field.isAnnotationPresent(Column.class);
			if(!isExist){
				continue;
			}
			Column column = field.getAnnotation(Column.class);
			String fieldName = column.value();
			//获取数据成员的名称
			String name = field.getName();
			String methodName = "get"+name.substring(0, 1).toUpperCase()+name.substring(1);
			try {
				//通过反射获取方法
				Method method = c.getMethod(methodName);
				//获取方法后，再次通过反射获取方法的返回值
				Object result = method.invoke(F);
				//如果获取的返回值是字符串并且为空或者返回的是整型并且为0，则continue循环
				if((result == null) || (result instanceof Integer && (Integer)result == 0)){
					continue;
				}else if(result instanceof String){
					String str = (String)result;
					if(fieldName.equals("email")){
						String [] strs = str.split(",");
						sql += fieldName+" in(";
						for (String string : strs) {
							//去掉最后一个逗号
							if(string.equals(strs[strs.length-1]))
							{
								sql += "'"+string+"'";
							}else{
								sql += "'"+string+"', ";
							}
						}
						sql += ")";
					}else{
						sql += fieldName+" = ";
						sql += "'"+(String)result+"' and ";
					}
				}else if(result instanceof Integer){
					sql += fieldName+" = ";
					sql += (Integer)result+" and ";
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch(InvocationTargetException ex){
				ex.printStackTrace();
			} catch(IllegalAccessException ex){
				ex.printStackTrace();
			}
		}
		//判断最后面是否有" and "
		if(sql.substring(sql.length()-5).equals(" and ")){
			return sql.substring(0,sql.length()-5);
		}
		//否则直接输出sql语句
		return sql;
	}
	
	public static void main(String[] args) {
		Filter f = new Filter();
		f.setAge(18);
//		f.setCity("上海");
//		f.setEmail("abc@163.com, def@163.com, ghi@163.com");
		System.out.println(query(f));
	}
}
