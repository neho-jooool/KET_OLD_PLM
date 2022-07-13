// Generated ITemInfo%4C2AE1090137: ? 08/18/10 20:12:08
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

package e3ps.project;

import e3ps.project.TemplateProject;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.String;
import wt.util.WTPropertyVetoException;

//##begin user.imports preserve=yes
//##end user.imports

//##begin ITemInfo%4C2AE1090137.doc preserve=no
/**
 * ????
 *
 * @version   1.0
 **/
//##end ITemInfo%4C2AE1090137.doc

public class ITemInfo implements Externalizable {


   // --- Attribute Section ---


   private static final String RESOURCE = "e3ps.project.projectResource";
   private static final String CLASSNAME = ITemInfo.class.getName();
   private String itemName;
   private String customer;
   private String usage;
   private String targetPrice;
   private String targetCost;
   private String targetRate;
   private String planSales;
   private String year1;
   private String year2;
   private String year3;
   private String year4;
   private String year5;
   private String year6;
   private String year7;
   private String year8;
   private String year9;
   private String year10;
   private TemplateProject project;
   static final long serialVersionUID = 1;
   public static final long EXTERNALIZATION_VERSION_UID = 6902580248197112195L;

   // WARNING: Fields placed in this section will not be generated into externalization methods.
   //##begin user.attributes preserve=yes
   //##end user.attributes

   //##begin static.initialization preserve=yes
   //##end static.initialization


   // --- Operation Section ---

   //##begin writeExternal%writeExternal.doc preserve=no
   /**
    * Writes the non-transient fields of this class to an external source.
    *
    * <BR><BR><B>Supported API: </B>false
    *
    * @param     output
    * @exception java.io.IOException
    **/
   //##end writeExternal%writeExternal.doc

   public void writeExternal( ObjectOutput output )
            throws IOException {
      //##begin writeExternal%writeExternal.body preserve=no

      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( customer );
      output.writeObject( itemName );
      output.writeObject( planSales );
      output.writeObject( project );
      output.writeObject( targetCost );
      output.writeObject( targetPrice );
      output.writeObject( targetRate );
      output.writeObject( usage );
      output.writeObject( year1 );
      output.writeObject( year10 );
      output.writeObject( year2 );
      output.writeObject( year3 );
      output.writeObject( year4 );
      output.writeObject( year5 );
      output.writeObject( year6 );
      output.writeObject( year7 );
      output.writeObject( year8 );
      output.writeObject( year9 );
      //##end writeExternal%writeExternal.body
   }

   //##begin readExternal%readExternal.doc preserve=no
   /**
    * Reads the non-transient fields of this class from an external source.
    *
    * <BR><BR><B>Supported API: </B>false
    *
    * @param     input
    * @exception java.io.IOException
    * @exception java.lang.ClassNotFoundException
    **/
   //##end readExternal%readExternal.doc

   public void readExternal( ObjectInput input )
            throws IOException, ClassNotFoundException {
      //##begin readExternal%readExternal.body preserve=no

      long readSerialVersionUID = input.readLong();                // consume UID

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID ) {  // if current version UID
         customer = (String)input.readObject();
         itemName = (String)input.readObject();
         planSales = (String)input.readObject();
         project = (TemplateProject)input.readObject();
         targetCost = (String)input.readObject();
         targetPrice = (String)input.readObject();
         targetRate = (String)input.readObject();
         usage = (String)input.readObject();
         year1 = (String)input.readObject();
         year10 = (String)input.readObject();
         year2 = (String)input.readObject();
         year3 = (String)input.readObject();
         year4 = (String)input.readObject();
         year5 = (String)input.readObject();
         year6 = (String)input.readObject();
         year7 = (String)input.readObject();
         year8 = (String)input.readObject();
         year9 = (String)input.readObject();
      }
      else
         throw new java.io.InvalidClassException( CLASSNAME, "Local class not compatible:"
                           + " stream classdesc externalizationVersionUID=" + readSerialVersionUID 
                           + " local class externalizationVersionUID=" + EXTERNALIZATION_VERSION_UID );
      //##end readExternal%readExternal.body
   }

   //##begin getItemName%4C2AE1560384g.doc preserve=no
   /**
    * Gets the value of the attribute: itemName; ??
    *
    * @return    String
    **/
   //##end getItemName%4C2AE1560384g.doc

   public String getItemName() {
      //##begin getItemName%4C2AE1560384g.body preserve=no

      return itemName;
      //##end getItemName%4C2AE1560384g.body
   }

   //##begin setItemName%4C2AE1560384s.doc preserve=no
   /**
    * Sets the value of the attribute: itemName; ??
    *
    * @param     a_ItemName
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setItemName%4C2AE1560384s.doc

   public void setItemName( String a_ItemName )
            throws WTPropertyVetoException {
      //##begin setItemName%4C2AE1560384s.body preserve=no

      itemNameValidate( a_ItemName );   // throws exception if not valid
      itemName = a_ItemName;
      //##end setItemName%4C2AE1560384s.body
   }

   //##begin itemNameValidate%4C2AE1560384.doc preserve=no
   /**
    * @param     a_ItemName
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end itemNameValidate%4C2AE1560384.doc

   private void itemNameValidate( String a_ItemName )
            throws WTPropertyVetoException {
      if ( a_ItemName != null && a_ItemName.length() > 200 ) {   // upper limit check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "itemName" ), "200" };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT, args,
               new java.beans.PropertyChangeEvent( this, "itemName", itemName, a_ItemName ) );
      }
   }

   //##begin getCustomer%4C2AE17603C8g.doc preserve=no
   /**
    * Gets the value of the attribute: customer; ?????
    *
    * @return    String
    **/
   //##end getCustomer%4C2AE17603C8g.doc

   public String getCustomer() {
      //##begin getCustomer%4C2AE17603C8g.body preserve=no

      return customer;
      //##end getCustomer%4C2AE17603C8g.body
   }

   //##begin setCustomer%4C2AE17603C8s.doc preserve=no
   /**
    * Sets the value of the attribute: customer; ?????
    *
    * @param     a_Customer
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setCustomer%4C2AE17603C8s.doc

   public void setCustomer( String a_Customer )
            throws WTPropertyVetoException {
      //##begin setCustomer%4C2AE17603C8s.body preserve=no

      customerValidate( a_Customer );   // throws exception if not valid
      customer = a_Customer;
      //##end setCustomer%4C2AE17603C8s.body
   }

   //##begin customerValidate%4C2AE17603C8.doc preserve=no
   /**
    * @param     a_Customer
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end customerValidate%4C2AE17603C8.doc

   private void customerValidate( String a_Customer )
            throws WTPropertyVetoException {
      if ( a_Customer != null && a_Customer.length() > 200 ) {   // upper limit check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "customer" ), "200" };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT, args,
               new java.beans.PropertyChangeEvent( this, "customer", customer, a_Customer ) );
      }
   }

   //##begin getUsage%4C2AE59400E4g.doc preserve=no
   /**
    * Gets the value of the attribute: usage; usage
    *
    * @return    String
    **/
   //##end getUsage%4C2AE59400E4g.doc

   public String getUsage() {
      //##begin getUsage%4C2AE59400E4g.body preserve=no

      return usage;
      //##end getUsage%4C2AE59400E4g.body
   }

   //##begin setUsage%4C2AE59400E4s.doc preserve=no
   /**
    * Sets the value of the attribute: usage; usage
    *
    * @param     a_Usage
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setUsage%4C2AE59400E4s.doc

   public void setUsage( String a_Usage )
            throws WTPropertyVetoException {
      //##begin setUsage%4C2AE59400E4s.body preserve=no

      usageValidate( a_Usage );   // throws exception if not valid
      usage = a_Usage;
      //##end setUsage%4C2AE59400E4s.body
   }

   //##begin usageValidate%4C2AE59400E4.doc preserve=no
   /**
    * @param     a_Usage
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end usageValidate%4C2AE59400E4.doc

   private void usageValidate( String a_Usage )
            throws WTPropertyVetoException {
      if ( a_Usage != null && a_Usage.length() > 200 ) {   // upper limit check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "usage" ), "200" };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT, args,
               new java.beans.PropertyChangeEvent( this, "usage", usage, a_Usage ) );
      }
   }

   //##begin getTargetPrice%4C2AE178005Bg.doc preserve=no
   /**
    * Gets the value of the attribute: targetPrice; ?????
    *
    * @return    String
    **/
   //##end getTargetPrice%4C2AE178005Bg.doc

   public String getTargetPrice() {
      //##begin getTargetPrice%4C2AE178005Bg.body preserve=no

      return targetPrice;
      //##end getTargetPrice%4C2AE178005Bg.body
   }

   //##begin setTargetPrice%4C2AE178005Bs.doc preserve=no
   /**
    * Sets the value of the attribute: targetPrice; ?????
    *
    * @param     a_TargetPrice
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setTargetPrice%4C2AE178005Bs.doc

   public void setTargetPrice( String a_TargetPrice )
            throws WTPropertyVetoException {
      //##begin setTargetPrice%4C2AE178005Bs.body preserve=no

      targetPriceValidate( a_TargetPrice );   // throws exception if not valid
      targetPrice = a_TargetPrice;
      //##end setTargetPrice%4C2AE178005Bs.body
   }

   //##begin targetPriceValidate%4C2AE178005B.doc preserve=no
   /**
    * @param     a_TargetPrice
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end targetPriceValidate%4C2AE178005B.doc

   private void targetPriceValidate( String a_TargetPrice )
            throws WTPropertyVetoException {
      if ( a_TargetPrice != null && a_TargetPrice.length() > 200 ) {   // upper limit check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "targetPrice" ), "200" };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT, args,
               new java.beans.PropertyChangeEvent( this, "targetPrice", targetPrice, a_TargetPrice ) );
      }
   }

   //##begin getTargetCost%4C2AE4250072g.doc preserve=no
   /**
    * Gets the value of the attribute: targetCost; ?? ????
    *
    * @return    String
    **/
   //##end getTargetCost%4C2AE4250072g.doc

   public String getTargetCost() {
      //##begin getTargetCost%4C2AE4250072g.body preserve=no

      return targetCost;
      //##end getTargetCost%4C2AE4250072g.body
   }

   //##begin setTargetCost%4C2AE4250072s.doc preserve=no
   /**
    * Sets the value of the attribute: targetCost; ?? ????
    *
    * @param     a_TargetCost
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setTargetCost%4C2AE4250072s.doc

   public void setTargetCost( String a_TargetCost )
            throws WTPropertyVetoException {
      //##begin setTargetCost%4C2AE4250072s.body preserve=no

      targetCostValidate( a_TargetCost );   // throws exception if not valid
      targetCost = a_TargetCost;
      //##end setTargetCost%4C2AE4250072s.body
   }

   //##begin targetCostValidate%4C2AE4250072.doc preserve=no
   /**
    * @param     a_TargetCost
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end targetCostValidate%4C2AE4250072.doc

   private void targetCostValidate( String a_TargetCost )
            throws WTPropertyVetoException {
      if ( a_TargetCost != null && a_TargetCost.length() > 200 ) {   // upper limit check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "targetCost" ), "200" };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT, args,
               new java.beans.PropertyChangeEvent( this, "targetCost", targetCost, a_TargetCost ) );
      }
   }

   //##begin getTargetRate%4C2AE4560248g.doc preserve=no
   /**
    * Gets the value of the attribute: targetRate; ?? ???
    *
    * @return    String
    **/
   //##end getTargetRate%4C2AE4560248g.doc

   public String getTargetRate() {
      //##begin getTargetRate%4C2AE4560248g.body preserve=no

      return targetRate;
      //##end getTargetRate%4C2AE4560248g.body
   }

   //##begin setTargetRate%4C2AE4560248s.doc preserve=no
   /**
    * Sets the value of the attribute: targetRate; ?? ???
    *
    * @param     a_TargetRate
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setTargetRate%4C2AE4560248s.doc

   public void setTargetRate( String a_TargetRate )
            throws WTPropertyVetoException {
      //##begin setTargetRate%4C2AE4560248s.body preserve=no

      targetRateValidate( a_TargetRate );   // throws exception if not valid
      targetRate = a_TargetRate;
      //##end setTargetRate%4C2AE4560248s.body
   }

   //##begin targetRateValidate%4C2AE4560248.doc preserve=no
   /**
    * @param     a_TargetRate
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end targetRateValidate%4C2AE4560248.doc

   private void targetRateValidate( String a_TargetRate )
            throws WTPropertyVetoException {
      if ( a_TargetRate != null && a_TargetRate.length() > 200 ) {   // upper limit check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "targetRate" ), "200" };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT, args,
               new java.beans.PropertyChangeEvent( this, "targetRate", targetRate, a_TargetRate ) );
      }
   }

   //##begin getPlanSales%4C2AE49602EDg.doc preserve=no
   /**
    * Gets the value of the attribute: planSales; ?? ?????
    *
    * @return    String
    **/
   //##end getPlanSales%4C2AE49602EDg.doc

   public String getPlanSales() {
      //##begin getPlanSales%4C2AE49602EDg.body preserve=no

      return planSales;
      //##end getPlanSales%4C2AE49602EDg.body
   }

   //##begin setPlanSales%4C2AE49602EDs.doc preserve=no
   /**
    * Sets the value of the attribute: planSales; ?? ?????
    *
    * @param     a_PlanSales
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setPlanSales%4C2AE49602EDs.doc

   public void setPlanSales( String a_PlanSales )
            throws WTPropertyVetoException {
      //##begin setPlanSales%4C2AE49602EDs.body preserve=no

      planSalesValidate( a_PlanSales );   // throws exception if not valid
      planSales = a_PlanSales;
      //##end setPlanSales%4C2AE49602EDs.body
   }

   //##begin planSalesValidate%4C2AE49602ED.doc preserve=no
   /**
    * @param     a_PlanSales
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end planSalesValidate%4C2AE49602ED.doc

   private void planSalesValidate( String a_PlanSales )
            throws WTPropertyVetoException {
      if ( a_PlanSales != null && a_PlanSales.length() > 200 ) {   // upper limit check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "planSales" ), "200" };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT, args,
               new java.beans.PropertyChangeEvent( this, "planSales", planSales, a_PlanSales ) );
      }
   }

   //##begin getYear1%4C2AE4DA01B9g.doc preserve=no
   /**
    * Gets the value of the attribute: year1; 1?? ????
    *
    * @return    String
    **/
   //##end getYear1%4C2AE4DA01B9g.doc

   public String getYear1() {
      //##begin getYear1%4C2AE4DA01B9g.body preserve=no

      return year1;
      //##end getYear1%4C2AE4DA01B9g.body
   }

   //##begin setYear1%4C2AE4DA01B9s.doc preserve=no
   /**
    * Sets the value of the attribute: year1; 1?? ????
    *
    * @param     a_Year1
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setYear1%4C2AE4DA01B9s.doc

   public void setYear1( String a_Year1 )
            throws WTPropertyVetoException {
      //##begin setYear1%4C2AE4DA01B9s.body preserve=no

      year1Validate( a_Year1 );   // throws exception if not valid
      year1 = a_Year1;
      //##end setYear1%4C2AE4DA01B9s.body
   }

   //##begin year1Validate%4C2AE4DA01B9.doc preserve=no
   /**
    * @param     a_Year1
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end year1Validate%4C2AE4DA01B9.doc

   private void year1Validate( String a_Year1 )
            throws WTPropertyVetoException {
      if ( a_Year1 != null && a_Year1.length() > 200 ) {   // upper limit check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "year1" ), "200" };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT, args,
               new java.beans.PropertyChangeEvent( this, "year1", year1, a_Year1 ) );
      }
   }

   //##begin getYear2%4C2AE50A0084g.doc preserve=no
   /**
    * Gets the value of the attribute: year2; 2??
    *
    * @return    String
    **/
   //##end getYear2%4C2AE50A0084g.doc

   public String getYear2() {
      //##begin getYear2%4C2AE50A0084g.body preserve=no

      return year2;
      //##end getYear2%4C2AE50A0084g.body
   }

   //##begin setYear2%4C2AE50A0084s.doc preserve=no
   /**
    * Sets the value of the attribute: year2; 2??
    *
    * @param     a_Year2
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setYear2%4C2AE50A0084s.doc

   public void setYear2( String a_Year2 )
            throws WTPropertyVetoException {
      //##begin setYear2%4C2AE50A0084s.body preserve=no

      year2Validate( a_Year2 );   // throws exception if not valid
      year2 = a_Year2;
      //##end setYear2%4C2AE50A0084s.body
   }

   //##begin year2Validate%4C2AE50A0084.doc preserve=no
   /**
    * @param     a_Year2
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end year2Validate%4C2AE50A0084.doc

   private void year2Validate( String a_Year2 )
            throws WTPropertyVetoException {
      if ( a_Year2 != null && a_Year2.length() > 200 ) {   // upper limit check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "year2" ), "200" };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT, args,
               new java.beans.PropertyChangeEvent( this, "year2", year2, a_Year2 ) );
      }
   }

   //##begin getYear3%4C2AE50A02B6g.doc preserve=no
   /**
    * Gets the value of the attribute: year3; 3??
    *
    * @return    String
    **/
   //##end getYear3%4C2AE50A02B6g.doc

   public String getYear3() {
      //##begin getYear3%4C2AE50A02B6g.body preserve=no

      return year3;
      //##end getYear3%4C2AE50A02B6g.body
   }

   //##begin setYear3%4C2AE50A02B6s.doc preserve=no
   /**
    * Sets the value of the attribute: year3; 3??
    *
    * @param     a_Year3
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setYear3%4C2AE50A02B6s.doc

   public void setYear3( String a_Year3 )
            throws WTPropertyVetoException {
      //##begin setYear3%4C2AE50A02B6s.body preserve=no

      year3Validate( a_Year3 );   // throws exception if not valid
      year3 = a_Year3;
      //##end setYear3%4C2AE50A02B6s.body
   }

   //##begin year3Validate%4C2AE50A02B6.doc preserve=no
   /**
    * @param     a_Year3
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end year3Validate%4C2AE50A02B6.doc

   private void year3Validate( String a_Year3 )
            throws WTPropertyVetoException {
      if ( a_Year3 != null && a_Year3.length() > 200 ) {   // upper limit check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "year3" ), "200" };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT, args,
               new java.beans.PropertyChangeEvent( this, "year3", year3, a_Year3 ) );
      }
   }

   //##begin getYear4%4C2AE50A03DEg.doc preserve=no
   /**
    * Gets the value of the attribute: year4; 4??
    *
    * @return    String
    **/
   //##end getYear4%4C2AE50A03DEg.doc

   public String getYear4() {
      //##begin getYear4%4C2AE50A03DEg.body preserve=no

      return year4;
      //##end getYear4%4C2AE50A03DEg.body
   }

   //##begin setYear4%4C2AE50A03DEs.doc preserve=no
   /**
    * Sets the value of the attribute: year4; 4??
    *
    * @param     a_Year4
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setYear4%4C2AE50A03DEs.doc

   public void setYear4( String a_Year4 )
            throws WTPropertyVetoException {
      //##begin setYear4%4C2AE50A03DEs.body preserve=no

      year4Validate( a_Year4 );   // throws exception if not valid
      year4 = a_Year4;
      //##end setYear4%4C2AE50A03DEs.body
   }

   //##begin year4Validate%4C2AE50A03DE.doc preserve=no
   /**
    * @param     a_Year4
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end year4Validate%4C2AE50A03DE.doc

   private void year4Validate( String a_Year4 )
            throws WTPropertyVetoException {
      if ( a_Year4 != null && a_Year4.length() > 200 ) {   // upper limit check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "year4" ), "200" };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT, args,
               new java.beans.PropertyChangeEvent( this, "year4", year4, a_Year4 ) );
      }
   }

   //##begin getYear5%4C2AE50B00C1g.doc preserve=no
   /**
    * Gets the value of the attribute: year5; 5??
    *
    * @return    String
    **/
   //##end getYear5%4C2AE50B00C1g.doc

   public String getYear5() {
      //##begin getYear5%4C2AE50B00C1g.body preserve=no

      return year5;
      //##end getYear5%4C2AE50B00C1g.body
   }

   //##begin setYear5%4C2AE50B00C1s.doc preserve=no
   /**
    * Sets the value of the attribute: year5; 5??
    *
    * @param     a_Year5
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setYear5%4C2AE50B00C1s.doc

   public void setYear5( String a_Year5 )
            throws WTPropertyVetoException {
      //##begin setYear5%4C2AE50B00C1s.body preserve=no

      year5Validate( a_Year5 );   // throws exception if not valid
      year5 = a_Year5;
      //##end setYear5%4C2AE50B00C1s.body
   }

   //##begin year5Validate%4C2AE50B00C1.doc preserve=no
   /**
    * @param     a_Year5
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end year5Validate%4C2AE50B00C1.doc

   private void year5Validate( String a_Year5 )
            throws WTPropertyVetoException {
      if ( a_Year5 != null && a_Year5.length() > 200 ) {   // upper limit check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "year5" ), "200" };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT, args,
               new java.beans.PropertyChangeEvent( this, "year5", year5, a_Year5 ) );
      }
   }

   //##begin getYear6%4C2AE50B018Cg.doc preserve=no
   /**
    * Gets the value of the attribute: year6.
    *
    * @return    String
    **/
   //##end getYear6%4C2AE50B018Cg.doc

   public String getYear6() {
      //##begin getYear6%4C2AE50B018Cg.body preserve=no

      return year6;
      //##end getYear6%4C2AE50B018Cg.body
   }

   //##begin setYear6%4C2AE50B018Cs.doc preserve=no
   /**
    * Sets the value of the attribute: year6.
    *
    * @param     a_Year6
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setYear6%4C2AE50B018Cs.doc

   public void setYear6( String a_Year6 )
            throws WTPropertyVetoException {
      //##begin setYear6%4C2AE50B018Cs.body preserve=no

      year6Validate( a_Year6 );   // throws exception if not valid
      year6 = a_Year6;
      //##end setYear6%4C2AE50B018Cs.body
   }

   //##begin year6Validate%4C2AE50B018C.doc preserve=no
   /**
    * @param     a_Year6
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end year6Validate%4C2AE50B018C.doc

   private void year6Validate( String a_Year6 )
            throws WTPropertyVetoException {
      if ( a_Year6 != null && a_Year6.length() > 200 ) {   // upper limit check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "year6" ), "200" };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT, args,
               new java.beans.PropertyChangeEvent( this, "year6", year6, a_Year6 ) );
      }
   }

   //##begin getYear7%4C2AE50B0256g.doc preserve=no
   /**
    * Gets the value of the attribute: year7.
    *
    * @return    String
    **/
   //##end getYear7%4C2AE50B0256g.doc

   public String getYear7() {
      //##begin getYear7%4C2AE50B0256g.body preserve=no

      return year7;
      //##end getYear7%4C2AE50B0256g.body
   }

   //##begin setYear7%4C2AE50B0256s.doc preserve=no
   /**
    * Sets the value of the attribute: year7.
    *
    * @param     a_Year7
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setYear7%4C2AE50B0256s.doc

   public void setYear7( String a_Year7 )
            throws WTPropertyVetoException {
      //##begin setYear7%4C2AE50B0256s.body preserve=no

      year7Validate( a_Year7 );   // throws exception if not valid
      year7 = a_Year7;
      //##end setYear7%4C2AE50B0256s.body
   }

   //##begin year7Validate%4C2AE50B0256.doc preserve=no
   /**
    * @param     a_Year7
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end year7Validate%4C2AE50B0256.doc

   private void year7Validate( String a_Year7 )
            throws WTPropertyVetoException {
      if ( a_Year7 != null && a_Year7.length() > 200 ) {   // upper limit check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "year7" ), "200" };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT, args,
               new java.beans.PropertyChangeEvent( this, "year7", year7, a_Year7 ) );
      }
   }

   //##begin getYear8%4C2AE5730388g.doc preserve=no
   /**
    * Gets the value of the attribute: year8.
    *
    * @return    String
    **/
   //##end getYear8%4C2AE5730388g.doc

   public String getYear8() {
      //##begin getYear8%4C2AE5730388g.body preserve=no

      return year8;
      //##end getYear8%4C2AE5730388g.body
   }

   //##begin setYear8%4C2AE5730388s.doc preserve=no
   /**
    * Sets the value of the attribute: year8.
    *
    * @param     a_Year8
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setYear8%4C2AE5730388s.doc

   public void setYear8( String a_Year8 )
            throws WTPropertyVetoException {
      //##begin setYear8%4C2AE5730388s.body preserve=no

      year8Validate( a_Year8 );   // throws exception if not valid
      year8 = a_Year8;
      //##end setYear8%4C2AE5730388s.body
   }

   //##begin year8Validate%4C2AE5730388.doc preserve=no
   /**
    * @param     a_Year8
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end year8Validate%4C2AE5730388.doc

   private void year8Validate( String a_Year8 )
            throws WTPropertyVetoException {
      if ( a_Year8 != null && a_Year8.length() > 200 ) {   // upper limit check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "year8" ), "200" };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT, args,
               new java.beans.PropertyChangeEvent( this, "year8", year8, a_Year8 ) );
      }
   }

   //##begin getYear9%4C2AE57400E8g.doc preserve=no
   /**
    * Gets the value of the attribute: year9.
    *
    * @return    String
    **/
   //##end getYear9%4C2AE57400E8g.doc

   public String getYear9() {
      //##begin getYear9%4C2AE57400E8g.body preserve=no

      return year9;
      //##end getYear9%4C2AE57400E8g.body
   }

   //##begin setYear9%4C2AE57400E8s.doc preserve=no
   /**
    * Sets the value of the attribute: year9.
    *
    * @param     a_Year9
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setYear9%4C2AE57400E8s.doc

   public void setYear9( String a_Year9 )
            throws WTPropertyVetoException {
      //##begin setYear9%4C2AE57400E8s.body preserve=no

      year9Validate( a_Year9 );   // throws exception if not valid
      year9 = a_Year9;
      //##end setYear9%4C2AE57400E8s.body
   }

   //##begin year9Validate%4C2AE57400E8.doc preserve=no
   /**
    * @param     a_Year9
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end year9Validate%4C2AE57400E8.doc

   private void year9Validate( String a_Year9 )
            throws WTPropertyVetoException {
      if ( a_Year9 != null && a_Year9.length() > 200 ) {   // upper limit check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "year9" ), "200" };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT, args,
               new java.beans.PropertyChangeEvent( this, "year9", year9, a_Year9 ) );
      }
   }

   //##begin getYear10%4C2AE57501D0g.doc preserve=no
   /**
    * Gets the value of the attribute: year10.
    *
    * @return    String
    **/
   //##end getYear10%4C2AE57501D0g.doc

   public String getYear10() {
      //##begin getYear10%4C2AE57501D0g.body preserve=no

      return year10;
      //##end getYear10%4C2AE57501D0g.body
   }

   //##begin setYear10%4C2AE57501D0s.doc preserve=no
   /**
    * Sets the value of the attribute: year10.
    *
    * @param     a_Year10
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setYear10%4C2AE57501D0s.doc

   public void setYear10( String a_Year10 )
            throws WTPropertyVetoException {
      //##begin setYear10%4C2AE57501D0s.body preserve=no

      year10Validate( a_Year10 );   // throws exception if not valid
      year10 = a_Year10;
      //##end setYear10%4C2AE57501D0s.body
   }

   //##begin year10Validate%4C2AE57501D0.doc preserve=no
   /**
    * @param     a_Year10
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end year10Validate%4C2AE57501D0.doc

   private void year10Validate( String a_Year10 )
            throws WTPropertyVetoException {
      if ( a_Year10 != null && a_Year10.length() > 200 ) {   // upper limit check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "year10" ), "200" };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT, args,
               new java.beans.PropertyChangeEvent( this, "year10", year10, a_Year10 ) );
      }
   }

   //##begin getProject%4C2AE11E031Ag.doc preserve=no
   /**
    * Gets the object for the association that plays role: project.
    *
    * @return    TemplateProject
    **/
   //##end getProject%4C2AE11E031Ag.doc

   public TemplateProject getProject() {
      //##begin getProject%4C2AE11E031Ag.body preserve=no

      return project;
      //##end getProject%4C2AE11E031Ag.body
   }

   //##begin setProject%4C2AE11E031As.doc preserve=no
   /**
    * Sets the object for the association that plays role: project.
    *
    * @param     a_Project
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setProject%4C2AE11E031As.doc

   public void setProject( TemplateProject a_Project )
            throws WTPropertyVetoException {
      //##begin setProject%4C2AE11E031As.body preserve=no

      projectValidate( a_Project );   // throws exception if not valid
      project = a_Project;
      //##end setProject%4C2AE11E031As.body
   }

   //##begin projectValidate%4C2AE11E031A.doc preserve=no
   /**
    * @param     a_Project
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end projectValidate%4C2AE11E031A.doc

   private void projectValidate( TemplateProject a_Project )
            throws WTPropertyVetoException {
      if ( a_Project == null ) {                                  // required attribute check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "project" ) };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE, args,
               new java.beans.PropertyChangeEvent( this, "project", project, a_Project ) );
      }
   }

   //##begin user.operations preserve=yes
   //##end user.operations
}
