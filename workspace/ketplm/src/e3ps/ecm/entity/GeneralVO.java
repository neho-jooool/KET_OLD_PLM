/* bcwti
 *
 * Copyright (c) 2008 Parametric Technology Corporation (PTC). All Rights
 * Reserved.
 *
 * This software is the confidential and proprietary information of PTC
 * and is subject to the terms of a software license agreement. You shall
 * not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement.
 *
 * ecwti
 */

package e3ps.ecm.entity;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import ext.ket.shared.log.Kogger;

/**
 *
 * @version   1.0
 **/

public class GeneralVO implements Serializable {


   private static final String RESOURCE = "e3ps.ecm.entity.entityResource";
   private static final String CLASSNAME = GeneralVO.class.getName();
   private HashMap mapA;
   private boolean isB;



   public GeneralVO()
   {
       isB = true;
       mapA = new HashMap();
   }

   public boolean isSet(String s)
   {
       return mapA.containsKey(s.toUpperCase());
   }

   public void clear(String s)
   {
       if(mapA.containsKey(s.toUpperCase()))
           mapA.remove(s.toUpperCase());
       else
           return;
   }

   public void clearAll()
   {
       mapA.clear();
   }

   public void setMap(HashMap hashmap)
   {
       mapA = hashmap;
   }

   public void addMap(HashMap hashmap)
   {
       mapA.putAll(hashmap);
   }

   public void setObject(String s, Object obj)
   {
       mapA.put(s.toUpperCase(), obj);
   }

   public void set(String s, String s1)
   {
       setString(s, s1);
   }

   public void setString(String s, String s1)
   {
       mapA.put(s.toUpperCase(), s1);
   }

   public void setInt(String s, int i)
   {
       mapA.put(s.toUpperCase(), new Integer(i));
   }

   public void setShort(String s, short word0)
   {
       mapA.put(s.toUpperCase(), new Short(word0));
   }

   public void setLong(String s, long l)
   {
       mapA.put(s.toUpperCase(), new Long(l));
   }

   public void setDouble(String s, double d)
   {
       mapA.put(s.toUpperCase(), new Double(d));
   }

   public void setFloat(String s, float f)
   {
       mapA.put(s.toUpperCase(), new Float(f));
   }

   public void setDate(String s, Date date)
   {
       mapA.put(s.toUpperCase(), date);
   }

   public void setBoolean(String s, boolean flag)
   {
       mapA.put(s.toUpperCase(), new Boolean(flag));
   }

   public void setStringArray(String s, String as[])
   {
       mapA.put(s.toUpperCase(), as);
   }

   public void setArray(String s, GeneralVO ageneralvo[])
   {
       mapA.put(s.toUpperCase(), ageneralvo);
   }

   public void setBigInteger(String s, BigInteger biginteger)
   {
       mapA.put(s.toUpperCase(), biginteger);
   }

   public void set(String s, String s1, String s2)
   {
       if("short".equalsIgnoreCase(s1))
           mapA.put(s.toUpperCase(), new Short(s2));
       else
       if("int".equalsIgnoreCase(s1))
           mapA.put(s.toUpperCase(), new Integer(s2));
       else
       if("long".equalsIgnoreCase(s1))
           mapA.put(s.toUpperCase(), new Long(s2));
       else
       if("float".equalsIgnoreCase(s1))
           mapA.put(s.toUpperCase(), new Float(s2));
       else
       if("double".equalsIgnoreCase(s1))
           mapA.put(s.toUpperCase(), new Double(s2));
       else
       if("boolean".equalsIgnoreCase(s1))
           mapA.put(s.toUpperCase(), new Boolean(s2));
       else
           setString(s, s2);
   }

   public HashMap getMap()
   {
       return mapA;
   }

   public String[] getKeys()
   {
       return (String[])(String[])mapA.keySet().toArray(new String[0]);
   }

   public Object getObject(String s)
   {
       return getObject(s, isB);
   }

   public Object getObject(String s, boolean flag)
   {
       String s1 = s.toUpperCase();
       if(flag && !mapA.containsKey(s1))
           throw new GeneralVOException((new StringBuilder()).append("[GeneralVO] there's no value in [").append(s1).append("]").toString());
       else
           return mapA.get(s1);
   }

   public String get(String s)
   {
       return get(s, isB);
   }

   public String get(String s, boolean flag)
   {
       return getString(s, flag);
   }

   public String getString(String s)
   {
       return getString(s, isB);
   }

   public String getString(String s, boolean flag)
   {
       String s1 = s.toUpperCase();
       if(flag && !mapA.containsKey(s1))
           throw new GeneralVOException((new StringBuilder()).append("[GeneralVO] there's no value in [").append(s1).append("]").toString());
       Object obj = mapA.get(s1);
       String s2 = "";
       if(obj == null)
           return null;
       if(obj instanceof String)
           s2 = (String)obj;
       else
       if(flag)
           throw new GeneralVOException((new StringBuilder()).append("[GeneralVO] illegal type[").append(obj != null ? obj.getClass().getName() : "null").append("] in key[").append(s1).append("]").toString());
       return s2;
   }

   public int getInt(String s)
   {
       return getInt(s, isB);
   }

   public int getInt(String s, boolean flag)
   {
       String s1 = s.toUpperCase();
       if(flag && !mapA.containsKey(s1))
           throw new GeneralVOException((new StringBuilder()).append("[GeneralVO] there's no value in [").append(s1).append("]").toString());
       Object obj = mapA.get(s1);
       int i = 0;
       if(obj != null && (obj instanceof Integer))
           i = ((Integer)obj).intValue();
       else
       if(flag)
           throw new GeneralVOException((new StringBuilder()).append("[GeneralVO] illegal type[").append(obj != null ? obj.getClass().getName() : "null").append("] in key[").append(s1).append("]").toString());
       return i;
   }

   public short getShort(String s)
   {
       return getShort(s, isB);
   }

   public short getShort(String s, boolean flag)
   {
       String s1 = s.toUpperCase();
       if(flag && !mapA.containsKey(s1))
           throw new GeneralVOException((new StringBuilder()).append("[GeneralVO] there's no value in [").append(s1).append("]").toString());
       Object obj = mapA.get(s1);
       short word0 = 0;
       if(obj != null && (obj instanceof Short))
           word0 = ((Short)obj).shortValue();
       else
       if(flag)
           throw new GeneralVOException((new StringBuilder()).append("[GeneralVO] illegal type[").append(obj != null ? obj.getClass().getName() : "null").append("] in key[").append(s1).append("]").toString());
       return word0;
   }

   public long getLong(String s)
   {
       return getLong(s, isB);
   }

   public long getLong(String s, boolean flag)
   {
       String s1 = s.toUpperCase();
       if(flag && !mapA.containsKey(s1))
           throw new GeneralVOException((new StringBuilder()).append("[GeneralVO] there's no value in [").append(s1).append("]").toString());
       Object obj = mapA.get(s1);
       long l = 0L;
       if(obj != null && (obj instanceof Long))
           l = ((Long)obj).longValue();
       else
       if(flag)
           throw new GeneralVOException((new StringBuilder()).append("[GeneralVO] illegal type[").append(obj != null ? obj.getClass().getName() : "null").append("] in key[").append(s1).append("]").toString());
       return l;
   }

   public double getDouble(String s)
   {
       return getDouble(s, isB);
   }

   public double getDouble(String s, boolean flag)
   {
       String s1 = s.toUpperCase();
       if(flag && !mapA.containsKey(s1))
           throw new GeneralVOException((new StringBuilder()).append("[GeneralVO] there's no value in [").append(s1).append("]").toString());
       Object obj = mapA.get(s1);
       double d = 0.0D;
       if(obj != null && (obj instanceof Double))
           d = ((Double)obj).doubleValue();
       else
       if(flag)
           throw new GeneralVOException((new StringBuilder()).append("[GeneralVO] illegal type[").append(obj != null ? obj.getClass().getName() : "null").append("] in key[").append(s1).append("]").toString());
       return d;
   }

   public float getFloat(String s)
   {
       return getFloat(s, isB);
   }

   public float getFloat(String s, boolean flag)
       throws GeneralVOException
   {
       String s1 = s.toUpperCase();
       if(flag && !mapA.containsKey(s1))
           throw new GeneralVOException((new StringBuilder()).append("[GeneralVO] there's no value in [").append(s1).append("]").toString());
       Object obj = mapA.get(s1);
       float f = 0.0F;
       if(obj != null && (obj instanceof Float))
           f = ((Float)obj).floatValue();
       else
       if(flag)
           throw new GeneralVOException((new StringBuilder()).append("[GeneralVO] illegal type[").append(obj != null ? obj.getClass().getName() : "null").append("] in key[").append(s1).append("]").toString());
       return f;
   }

   public Date getDate(String s)
   {
       return getDate(s, isB);
   }

   public Date getDate(String s, boolean flag)
   {
       String s1 = s.toUpperCase();
       if(flag && !mapA.containsKey(s1))
           throw new GeneralVOException((new StringBuilder()).append("[GeneralVO] there's no value in [").append(s1).append("]").toString());
       Object obj = mapA.get(s1);
       Date date = null;
       if(obj == null)
           return null;
       if(obj instanceof Date)
           date = (Date)obj;
       else
       if(flag)
           throw new GeneralVOException((new StringBuilder()).append("[GeneralVO] illegal type[").append(obj != null ? obj.getClass().getName() : "null").append("] in key[").append(s1).append("]").toString());
       return date;
   }

   public boolean getBoolean(String s)
   {
       return getBoolean(s, isB);
   }

   public boolean getBoolean(String s, boolean flag)
   {
       String s1 = s.toUpperCase();
       if(flag && !mapA.containsKey(s1))
           throw new GeneralVOException((new StringBuilder()).append("[GeneralVO] there's no value in [").append(s1).append("]").toString());
       Object obj = mapA.get(s1);
       boolean flag1 = false;
       if(obj != null && (obj instanceof Boolean))
           flag1 = ((Boolean)obj).booleanValue();
       else
       if(flag)
           throw new GeneralVOException((new StringBuilder()).append("[GeneralVO] illegal type[").append(obj != null ? obj.getClass().getName() : "null").append("] in key[").append(s1).append("]").toString());
       return flag1;
   }

   public String[] getStringArray(String s)
   {
       return getStringArray(s, isB);
   }

   public String[] getStringArray(String s, boolean flag)
   {
       String s1 = s.toUpperCase();
       if(!mapA.containsKey(s1))
           return null;
       String as[] = null;
       Object obj = mapA.get(s1);
       if(obj == null)
           return null;
       Class class1 = obj.getClass();
       if(class1.isArray())
           as = (String[])(String[])mapA.get(s1);
       else
       if(flag)
           throw new GeneralVOException((new StringBuilder()).append("[GeneralVO] illegal type[").append(obj != null ? obj.getClass().getName() : "null").append("] in key[").append(s1).append("]").toString());
       return as;
   }

   public GeneralVO[] getArray(String s)
   {
       return getArray(s, isB);
   }

   public GeneralVO[] getArray(String s, boolean flag)
   {
       String s1 = s.toUpperCase();
       if(!mapA.containsKey(s1))
           return null;
       GeneralVO ageneralvo[] = null;
       Object obj = mapA.get(s1);
       if(obj == null)
           return null;
       Class class1 = obj.getClass();
       if(class1.isArray())
           ageneralvo = (GeneralVO[])(GeneralVO[])mapA.get(s1);
       else
       if(flag)
           throw new GeneralVOException((new StringBuilder()).append("[GeneralVO] illegal type[").append(obj != null ? obj.getClass().getName() : "null").append("] in key[").append(s1).append("]").toString());
       return ageneralvo;
   }

   public BigInteger getBigInteger(String s)
   {
       return getBigInteger(s, isB);
   }

   public BigInteger getBigInteger(String s, boolean flag)
   {
       String s1 = s.toUpperCase();
       if(flag && !mapA.containsKey(s1))
           throw new GeneralVOException((new StringBuilder()).append("[GeneralVO] there's no value in [").append(s1).append("]").toString());
       Object obj = mapA.get(s1);
       BigInteger biginteger = null;
       if(obj == null)
           return null;
       if(obj instanceof BigInteger)
           biginteger = (BigInteger)obj;
       else
       if(flag)
           throw new GeneralVOException((new StringBuilder()).append("[GeneralVO] illegal type[").append(obj != null ? obj.getClass().getName() : "null").append("] in key[").append(s1).append("]").toString());
       return biginteger;
   }

   public String toString()
   {
       StringBuilder stringbuilder = new StringBuilder();
       stringbuilder.append((new StringBuilder()).append(getClass().getName()).append(":{").toString());
       Iterator iterator = mapA.keySet().iterator();
       boolean flag = true;
       Object obj = null;
       Object obj2 = null;
       String s = "";
       while(iterator.hasNext()) 
       {
           if(flag)
               flag = false;
           else
               stringbuilder.append(",");
           String s1 = (String)iterator.next();
           stringbuilder.append((new StringBuilder()).append(s1).append("=").toString());
           Object obj1 = mapA.get(s1);
           if(obj1 != null)
           {
               Class class1 = obj1.getClass();
               if(class1.isArray())
               {
                   int i = Array.getLength(obj1);
                   stringbuilder.append('[');
                   if(i > 0)
                   {
                       for(int j = 0; j < i; j++)
                       {
                           if(j != 0)
                               stringbuilder.append(',');
                           stringbuilder.append(Array.get(obj1, j));
                       }

                   }
                   stringbuilder.append(']');
               } else
               {
                   stringbuilder.append(obj1);
               }
           } else
           {
               stringbuilder.append(obj1);
           }
       }
       stringbuilder.append("}");
       return stringbuilder.toString();
   }

   public static void main(String args[])
   {
       GeneralVO generalvo = new GeneralVO();
       generalvo.setInt("int", 1);
       String args1[] = {
           "111", "222"
       };
       generalvo.setStringArray("arr", args1);
       generalvo.setDate("date", new Date());
       generalvo.setDouble("double", 555.5D);
       generalvo.setFloat("float", 444F);
       generalvo.setLong("long", 667L);
       generalvo.setShort("short", (short)222);
       generalvo.setBoolean("boolean", false);
       GeneralVO generalvo1 = new GeneralVO();
       generalvo1.setDate("date", new Date());
       generalvo1.setStringArray("arr", args1);
       generalvo.setObject("childVO", generalvo1);
       GeneralVO ageneralvo[] = new GeneralVO[2];
       GeneralVO generalvo2 = new GeneralVO();
       generalvo2.setDate("date", new Date());
       GeneralVO generalvo3 = new GeneralVO();
       generalvo3.setDate("date", new Date());
       ageneralvo[0] = generalvo2;
       ageneralvo[1] = generalvo3;
       generalvo.setObject("childVOArr", ageneralvo);
       GeneralVO ageneralvo1[] = (GeneralVO[])(GeneralVO[])generalvo.getObject("childVOArr");
       Kogger.debug(GeneralVO.class, (new StringBuilder()).append("generalVO.get(\"childVOArr\")=").append(ageneralvo1).toString());
       Kogger.debug(GeneralVO.class, (new StringBuilder()).append("generalVO=").append(generalvo).toString());
       GeneralVO generalvo4 = new GeneralVO();
       generalvo4.setString("aa", "bb");
       Kogger.debug(GeneralVO.class, (new StringBuilder()).append("aa=").append(generalvo4).toString());
       Kogger.debug(GeneralVO.class, (new StringBuilder()).append("isSet 1=").append(generalvo.isSet("childVO")).toString());
       Kogger.debug(GeneralVO.class, (new StringBuilder()).append("isSet 2=").append(generalvo.isSet("arr")).toString());
       generalvo.clear("arr");
       Kogger.debug(GeneralVO.class, (new StringBuilder()).append("after clear=").append(generalvo.isSet("arr")).toString());
       String args2[] = generalvo.getKeys();
       int i = args2.length;
       for(int j = 0; j < i; j++)
           System.out.print((new StringBuilder()).append(" getKeys(").append(j).append(") ").append(args2[j]).toString());

       generalvo.clearAll();
       Kogger.debug(GeneralVO.class, (new StringBuilder()).append("generalVO=").append(generalvo).toString());
       BigInteger biginteger = new BigInteger("99999999999999999999");
       generalvo.setBigInteger("big", biginteger);
       Kogger.debug(GeneralVO.class, (new StringBuilder()).append("generalVO=").append(generalvo).toString());
   }
}
