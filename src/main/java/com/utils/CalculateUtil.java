package com.utils;

import java.lang.reflect.Field;
import java.util.List;

public class CalculateUtil {

	
	
	/**
	 * 
	 * @param datas
	 * @return 小计: - - - 45 87.9 - 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings("unchecked")
	public static <X> X sum(List<X> datas) {
		if(CollectionUtil.isBlank(datas)){
			return null;
		}
		X sum = null;
		try {
			Class clazz = datas.get(0).getClass();
			sum = (X) clazz.newInstance();
			Field [] fields = clazz.getDeclaredFields();
			for(Field f:fields){  
	            f.setAccessible(true);  
	        }  
			for(X x : datas){
				for(Field f : fields){
					if("serialVersionUID".equals(f.getName())){
						continue;
					}
					Object value = f.get(x);
					if(value == null){
						continue;
					}
					if(value instanceof Integer){
						Object total = f.get(sum) == null ? 0 : f.get(sum);
						Number newTotal = ((Number)total).intValue() + ((Number)value).intValue() ;
						f.set(sum, newTotal);
					}else if(value instanceof Double){
						Object total = f.get(sum) == null ? 0 : f.get(sum);
						Number newTotal = ((Number)total).doubleValue() + ((Number)value).doubleValue() ;
						f.set(sum, newTotal);
					}else if(value instanceof Float){
						Object total = f.get(sum) == null ? 0 : f.get(sum);
						Number newTotal = ((Number)total).floatValue() + ((Number)value).floatValue() ;
						f.set(sum, newTotal);
					}else if(value instanceof Short){
						Object total = f.get(sum) == null ? 0 : f.get(sum);
						Number newTotal = ((Number)total).shortValue() + ((Number)value).shortValue() ;
						f.set(sum, newTotal);
					}else if (value instanceof Long){
						Object total = f.get(sum) == null ? 0 : f.get(sum);
						Number newTotal = ((Number)total).longValue() + ((Number)value).longValue() ;
						f.set(sum, newTotal);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return sum;
	}
	
}
