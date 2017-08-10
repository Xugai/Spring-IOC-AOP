package Annotation;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Test {
	
	public static String query(Filter F){
		//1���Ȼ�ȡ������ͨ�����ע���ȡ
		Class c = F.getClass();
		//�ж��Ƿ���ע�⣬���У����ø�ע��ֵ
		@SuppressWarnings("unchecked")
		boolean exist = c.isAnnotationPresent(Table.class);
		if(!exist){
			return null;
		}
		@SuppressWarnings("unchecked")
		Table t = (Table)c.getAnnotation(Table.class);
		String tableName = t.value();
		
		String sql = "select * from "+tableName+" where ";
		
		//��������ȡ���е��ֶ���
		Field [] fs = c.getDeclaredFields();
		
		for (Field field : fs) {
			//��ͨ�������ȡÿ�����ݳ�Ա��ע��ֵ��Ҳ����SQL����ֶ���
			boolean isExist = field.isAnnotationPresent(Column.class);
			if(!isExist){
				continue;
			}
			Column column = field.getAnnotation(Column.class);
			String fieldName = column.value();
			//��ȡ���ݳ�Ա������
			String name = field.getName();
			String methodName = "get"+name.substring(0, 1).toUpperCase()+name.substring(1);
			try {
				//ͨ�������ȡ����
				Method method = c.getMethod(methodName);
				//��ȡ�������ٴ�ͨ�������ȡ�����ķ���ֵ
				Object result = method.invoke(F);
				//�����ȡ�ķ���ֵ���ַ�������Ϊ�ջ��߷��ص������Ͳ���Ϊ0����continueѭ��
				if((result == null) || (result instanceof Integer && (Integer)result == 0)){
					continue;
				}else if(result instanceof String){
					String str = (String)result;
					if(fieldName.equals("email")){
						String [] strs = str.split(",");
						sql += fieldName+" in(";
						for (String string : strs) {
							//ȥ�����һ������
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
		//�ж�������Ƿ���" and "
		if(sql.substring(sql.length()-5).equals(" and ")){
			return sql.substring(0,sql.length()-5);
		}
		//����ֱ�����sql���
		return sql;
	}
	
	public static void main(String[] args) {
		Filter f = new Filter();
		f.setAge(18);
//		f.setCity("�Ϻ�");
//		f.setEmail("abc@163.com, def@163.com, ghi@163.com");
		System.out.println(query(f));
	}
}
