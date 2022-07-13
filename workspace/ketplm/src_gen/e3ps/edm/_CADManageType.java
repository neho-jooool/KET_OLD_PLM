package e3ps.edm;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _CADManageType extends wt.fc.EnumeratedType {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.edm.edmResource";
   static final java.lang.String CLASSNAME = new CADManageType().getClass().getName();

   static final java.lang.String CLASS_RESOURCE = "e3ps.edm.CADManageTypeRB";
   @SuppressWarnings("rawtypes") static java.util.Hashtable localeSets;

   private static volatile wt.fc.EnumeratedType[] valueSet;
   static wt.fc.EnumeratedType[] _valueSet() {
      if (valueSet == null) synchronized (_CADManageType.class) {
         try { if (valueSet == null) valueSet = initializeLocaleSet(null); }
         catch (java.lang.Throwable t) { throw new java.lang.ExceptionInInitializerError(t); }
      }
      return valueSet;
   }

   public static CADManageType newCADManageType(int secretHandshake) throws java.lang.IllegalAccessException {
      validateFriendship(secretHandshake);
      return new CADManageType();
   }

   public static CADManageType toCADManageType(java.lang.String internal_value) throws wt.util.WTInvalidParameterException {
      return (CADManageType) toEnumeratedType(internal_value, _valueSet());
   }

   public static CADManageType getCADManageTypeDefault() {
      return (CADManageType) defaultEnumeratedType(_valueSet());
   }

   public static CADManageType[] getCADManageTypeSet() {
      CADManageType[] set = new CADManageType[_valueSet().length];
      java.lang.System.arraycopy(valueSet, 0, set, 0, valueSet.length);
      return set;
   }

   public wt.fc.EnumeratedType[] getValueSet() {
      return getCADManageTypeSet();
   }

   protected wt.fc.EnumeratedType[] valueSet() {
      return _valueSet();
   }

   @SuppressWarnings("rawtypes")
   protected wt.fc.EnumeratedType[] getLocaleSet(java.util.Locale locale) {
      wt.fc.EnumeratedType[] request = null;

      if (localeSets == null) localeSets = new java.util.Hashtable();
      else request = (wt.fc.EnumeratedType[]) localeSets.get(locale);

      if (request == null) {
         try { request = initializeLocaleSet(locale); }
         catch (java.lang.Throwable t) { /* snuff, since generation of class ensures that exception will not be thrown */ }
         localeSets.put(locale, request);
      }

      return request;
   }

   static wt.fc.EnumeratedType[] initializeLocaleSet(java.util.Locale locale) throws java.lang.Throwable {
      return instantiateSet(CADManageType.class.getMethod( "newCADManageType", new java.lang.Class<?>[] { java.lang.Integer.TYPE }), CLASS_RESOURCE, locale);
   }
}
