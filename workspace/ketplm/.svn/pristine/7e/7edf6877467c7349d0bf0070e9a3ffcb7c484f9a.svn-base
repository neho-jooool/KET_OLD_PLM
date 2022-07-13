/**
 * @(#) CharUtil.java Copyright (c) jerred. All rights reserverd
 * @version 1.00
 * @since jdk 1.4.02
 * @createdate 2004. 3. 3.
 * @author Cho Sung Ok, jerred@bcline.com
 * @desc
 */

package e3ps.common.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

/**
 * Object 의 복제. 일반적으로 <code>java.lang.Object.clone()</code> 함수를 를 사용하여 Object를
 * 복제하면 Object내에 있는 Primitive type을 제외한 Object field들은 복제가 되는 것이 아니라 같은 Object의
 * reference를 갖게 된다. <br>
 * 그러나 이 Method들을 사용하면 각 field의 동일한 Object를 새로 복제(clone)하여준다.
 */
public final class CloneUtil {
	/**
	 * 객체 생성을 방지하기 위해서 디폴트 생성자를 Private로 선언
	 */
	private CloneUtil() {
	}

	/**
	 * Object[] 의 복제. Object의 Array 를 복제(clone)하여 새로운 Instance를 만들어 줍니다.
	 * 
	 * @param objects
	 *                  java.lang.Object[]
	 * @return java.lang.Object[]
	 */
	public synchronized static Object[] clone(Object[] objects) {
		int length = objects.length;
		Class c = objects.getClass ().getComponentType ();
		Object array = Array.newInstance ( c , length );

		for (int i = 0; i < length; i++) {
			Array.set ( array , i , CloneUtil.clone ( objects[i] ) );
		}
		return (Object[]) array;
	}

	/**
	 * Object 의 복제
	 * 
	 * @param object
	 *                  java.lang.Object
	 * @return java.lang.Object
	 */
	public synchronized static Object clone(Object object) {
		Class c = object.getClass ();
		Object newObject = null;
		try {
			newObject = c.newInstance ();
		} catch (Exception e) {
			return null;
		}

		Field[] field = c.getFields ();
		for (int i = 0; i < field.length; i++) {
			try {
				Object f = field[i].get ( object );
				field[i].set ( newObject , f );
			} catch (Exception e) {
			}
		}
		return newObject;
	}
}
