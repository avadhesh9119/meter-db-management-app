package com.global.meter.v3.utils;

public class DivFactorConstants {
	public static String getUiNameValue(String keyName) {
		String fieldName = "";

		if ("IHM_1ph_InstantaneousRead".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.IHM_1PH_INSTANTANEOUSREAD;
		}
		else if("IHM_3ph_InstantaneousRead".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.IHM_3PH_INSTANTANEOUSREAD;
		}
		else if ("IHM_1ph_DailyLoadProfile".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.IHM_1PH_DAILYLOADPROFILE;
		}
		else if ("IHM_3ph_DailyLoadProfile".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.IHM_3PH_DAILYLOADPROFILE;
		}
		else if ("IHM_1ph_DeltaLoadProfile".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.IHM_1PH_DELTALOADPROFILE;
		}
		else if ("IHM_3ph_DeltaLoadProfile".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.IHM_3PH_DELTALOADPROFILE;
		}
		else if ("IHM_1ph_BillingData".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.IHM_1PH_BILLINGDATA;
		}
		else if ("IHM_3ph_BillingData".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.IHM_3PH_BILLINGDATA;
		}
		else if ("IHM_1ph_CurrentRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.IHM_1PH_CURRENTRELATEDEVENTS;
		}
		else if ("IHM_3ph_CurrentRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.IHM_3PH_CURRENTRELATEDEVENTS;
		}
		else if ("IHM_1ph_OtherRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.IHM_1PH_OTHERRELATEDEVENTS;
		}
		else if ("IHM_3ph_OtherRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.IHM_3PH_OTHERRELATEDEVENTS;
		}
		else if ("IHM_3ph_VoltageRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.IHM_3PH_VOLTAGERELATEDEVENTS;
		}
		else if ("JPM_1ph_InstantaneousRead".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.JPM_1PH_INSTANTANEOUSREAD;
		}
		else if ("JPM_3ph_InstantaneousRead".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.JPM_3PH_INSTANTANEOUSREAD;
		}
		else if ("JPM_1ph_DailyLoadProfile".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.JPM_1PH_DAILYLOADPROFILE;
		}
		else if ("JPM_3ph_DailyLoadProfile".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.JPM_3PH_DAILYLOADPROFILE;
		}
		else if ("JPM_1ph_DeltaLoadProfile".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.JPM_1PH_DELTALOADPROFILE;
		}
		else if ("JPM_3ph_DeltaLoadProfile".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.JPM_3PH_DELTALOADPROFILE;
		}
		else if ("JPM_1ph_BillingData".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.JPM_1PH_BILLINGDATA;
		}
		else if ("JPM_3ph_BillingData".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.JPM_3PH_BILLINGDATA;
		}
		else if ("JPM_1ph_CurrentRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.JPM_1PH_CURRENTRELATEDEVENTS;
		}
		else if ("JPM_3ph_CurrentRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.JPM_3PH_CURRENTRELATEDEVENTS;
		}
		else if ("JPM_1ph_OtherRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.JPM_1PH_OTHERRELATEDEVENTS;
		}
		else if ("JPM_3ph_OtherRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.JPM_3PH_OTHERRELATEDEVENTS;
		}
		else if ("JPM_3ph_VoltageRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.JPM_3PH_VOLTAGERELATEDEVENTS;
		}
		else if ("FLA_1ph_InstantaneousRead".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.FLA_1PH_INSTANTANEOUSREAD;
		}
		else if ("FLA_3ph_InstantaneousRead".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.FLA_3PH_INSTANTANEOUSREAD;
		}
		else if ("FLA_1ph_DailyLoadProfile".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.FLA_1PH_DAILYLOADPROFILE;
		}
		else if ("FLA_3ph_DailyLoadProfile".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.FLA_3PH_DAILYLOADPROFILE;
		}
		else if ("FLA_1ph_DeltaLoadProfile".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.FLA_1PH_DELTALOADPROFILE;
		}
		else if ("FLA_3ph_DeltaLoadProfile".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.FLA_3PH_DELTALOADPROFILE;
		}
		else if ("FLA_1ph_BillingData".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.FLA_1PH_BILLINGDATA;
		}
		else if ("FLA_3ph_BillingData".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.FLA_3PH_BILLINGDATA;
		}
		else if ("FLA_1ph_CurrentRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.FLA_1PH_CURRENTRELATEDEVENTS;
		}
		else if ("FLA_3ph_CurrentRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.FLA_3PH_CURRENTRELATEDEVENTS;
		}
		else if ("FLA_1ph_OtherRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.FLA_1PH_OTHERRELATEDEVENTS;
		}
		else if ("FLA_3ph_OtherRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.FLA_3PH_OTHERRELATEDEVENTS;
		}
		else if ("FLA_3ph_VoltageRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.FLA_3PH_VOLTAGERELATEDEVENTS;
		}
		else if ("ALLIED_1ph_InstantaneousRead".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.ALLIED_1PH_INSTANTANEOUSREAD;
		}
		else if ("ALLIED_V5.62_1ph_InstantaneousRead".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.ALLIED_V5_62_1PH_INSTANTANEOUSREAD;
		}
		else if ("ALLIED_AW3.30_1ph_InstantaneousRead".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.ALLIED_AW3_30_1PH_INSTANTANEOUSREAD;
		}
		else if ("ALLIED_AW3.09_1ph_InstantaneousRead".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.ALLIED_AW3_09_1PH_INSTANTANEOUSREAD;
		}
		else if ("ALLIED_AW3.31_1ph_InstantaneousRead".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.ALLIED_AW3_31_1PH_INSTANTANEOUSREAD;
		}
		else if ("ALLIED_3ph_InstantaneousRead".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.ALLIED_3PH_INSTANTANEOUSREAD;
		}
		else if ("ALLIED_1ph_DailyLoadProfile".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.ALLIED_1PH_DAILYLOADPROFILE;
		}
		else if ("ALLIED_AW3.31_1ph_DailyLoadProfile".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.ALLIED_AW3_31_1PH_DAILYLOADPROFILE;
		}
		else if ("ALLIED_V5.62_1ph_DailyLoadProfile".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.ALLIED_V5_62_1PH_DAILYLOADPROFILE;
		}
		else if ("ALLIED_3ph_DailyLoadProfile".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.ALLIED_3PH_DAILYLOADPROFILE;
		}
		else if ("ALLIED_1ph_DeltaLoadProfile".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.ALLIED_1PH_DELTALOADPROFILE;
		}
		else if ("ALLIED_AW3.31_1ph_DeltaLoadProfile".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.ALLIED_AW3_31_1PH_DELTALOADPROFILE;
		}
		else if ("ALLIED_V5.62_1ph_DeltaLoadProfile".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.ALLIED_V5_62_1PH_DELTALOADPROFILE;
		}
		else if ("ALLIED_3ph_DeltaLoadProfile".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.ALLIED_3PH_DELTALOADPROFILE;
		}
		else if ("ALLIED_1ph_BillingData".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.ALLIED_1PH_BILLINGDATA;
		}
		else if ("ALLIED_AW3.31_1ph_BillingData".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.ALLIED_AW3_31_1PH_BILLINGDATA;
		}
		else if ("ALLIED_V5.62_1ph_BillingData".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.ALLIED_V5_62_1PH_BILLINGDATA;
		}
		else if ("ALLIED_3ph_BillingData".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.ALLIED_3PH_BILLINGDATA;
		}
		else if ("ALLIED_1ph_CurrentRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.ALLIED_1PH_CURRENTRELATEDEVENTS;
		}
		else if ("ALLIED_V5.62_1ph_CurrentRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.ALLIED_V5_62_1PH_CURRENTRELATEDEVENTS;
		}
		else if ("ALLIED_3ph_CurrentRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.ALLIED_3PH_CURRENTRELATEDEVENTS;
		}
		else if ("ALLIED_1ph_OtherRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.ALLIED_1PH_OTHERRELATEDEVENTS;
		}
		else if ("ALLIED_V5.62_1ph_OtherRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.ALLIED_V5_62_1PH_OTHERRELATEDEVENTS;
		}
		else if ("ALLIED_3ph_OtherRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.ALLIED_3PH_OTHERRELATEDEVENTS;
		}
		else if ("ALLIED_3ph_VoltageRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.ALLIED_3PH_VOLTAGERELATEDEVENTS;
		}
		else if ("LECS_1ph_InstantaneousRead".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.LECS_1PH_INSTANTANEOUSREAD;
		}
		else if ("LECS_3ph_InstantaneousRead".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.LECS_3PH_INSTANTANEOUSREAD;
		}
		else if ("LECS_1ph_DailyLoadProfile".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.LECS_1PH_DAILYLOADPROFILE;
		}
		else if ("LECS_3ph_DailyLoadProfile".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.LECS_3PH_DAILYLOADPROFILE;
		}
		else if ("LECS_1ph_DeltaLoadProfile".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.LECS_1PH_DELTALOADPROFILE;
		}
		else if ("LECS_3ph_DeltaLoadProfile".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.LECS_3PH_DELTALOADPROFILE;
		}
		else if ("LECS_1ph_BillingData".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.LECS_1PH_BILLINGDATA;
		}
		else if ("LECS_3ph_BillingData".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.LECS_3PH_BILLINGDATA;
		}
		else if ("LECS_1ph_CurrentRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.LECS_1PH_CURRENTRELATEDEVENTS;
		}
		else if ("LECS_3ph_CurrentRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.LECS_3PH_CURRENTRELATEDEVENTS;
		}
		else if ("LECS_1ph_OtherRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.LECS_1PH_OTHERRELATEDEVENTS;
		}
		else if ("LECS_3ph_OtherRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.LECS_3PH_OTHERRELATEDEVENTS;
		}
		else if ("LECS_3ph_VoltageRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.LECS_3PH_VOLTAGERELATEDEVENTS;
		}
		else if ("CPS_1ph_InstantaneousRead".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.CPS_1PH_INSTANTANEOUSREAD;
		}
		else if ("CPS_3ph_InstantaneousRead".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.CPS_3PH_INSTANTANEOUSREAD;
		}
		else if ("CPS_1ph_DailyLoadProfile".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.CPS_1PH_DAILYLOADPROFILE;
		}
		else if ("CPS_3ph_DailyLoadProfile".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.CPS_3PH_DAILYLOADPROFILE;
		}
		else if ("CPS_1ph_DeltaLoadProfile".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.CPS_1PH_DELTALOADPROFILE;
		}
		else if ("CPS_3ph_DeltaLoadProfile".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.CPS_3PH_DELTALOADPROFILE;
		}
		else if ("CPS_1ph_BillingData".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.CPS_1PH_BILLINGDATA;
		}
		else if ("CPS_3ph_BillingData".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.CPS_3PH_BILLINGDATA;
		}
		else if ("CPS_1ph_CurrentRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.CPS_1PH_CURRENTRELATEDEVENTS;
		}
		else if ("CPS_3ph_CurrentRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.CPS_3PH_CURRENTRELATEDEVENTS;
		}
		else if ("CPS_1ph_OtherRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.CPS_1PH_OTHERRELATEDEVENTS;
		}
		else if ("CPS_3ph_OtherRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.CPS_3PH_OTHERRELATEDEVENTS;
		}
		else if ("CPS_3ph_VoltageRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.CPS_3PH_VOLTAGERELATEDEVENTS;
		}
		else if ("CPS_ct_InstantaneousRead".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.CPS_CT_INSTANTANEOUSREAD;
		}
		else if ("CPS_ct_DailyLoadProfile".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.CPS_CT_DAILYLOADPROFILE;
		}
		else if ("CPS_ct_DeltaLoadProfile".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.CPS_CT_DELTALOADPROFILE;
		}
		else if ("CPS_ct_BillingData".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.CPS_CT_BILLINGDATA;
		}
		else if ("CPS_ct_CurrentRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.CPS_CT_CURRENTRELATEDEVENTS;
		}
		else if ("CPS_ct_OtherRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.CPS_CT_OTHERRELATEDEVENTS;
		}
		else if ("CPS_ct_VoltageRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.CPS_CT_VOLTAGERELATEDEVENTS;
		}
		else if ("ZEN_1ph_InstantaneousRead".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.ZEN_1PH_INSTANTANEOUSREAD;
		}
		else if ("ZEN_1ph_DailyLoadProfile".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.ZEN_1PH_DAILYLOADPROFILE;
		}
		else if ("ZEN_1ph_DeltaLoadProfile".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.ZEN_1PH_DELTALOADPROFILE;
		}
		else if ("ZEN_1ph_BillingData".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.ZEN_1PH_BILLINGDATA;
		}
		else if ("ZEN_1ph_VoltageRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.ZEN_1PH_VOLTAGERELATEDEVENTS;
		}
		else if ("ZEN_1ph_CurrentRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.ZEN_1PH_CURRENTRELATEDEVENTS;
		}
		else if ("ZEN_1ph_OtherRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.ZEN_1PH_OTHERRELATEDEVENTS;
		}
		else if ("ZEN_3ph_InstantaneousRead".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.ZEN_3PH_INSTANTANEOUSREAD;
		}
		else if ("ZEN_3ph_DailyLoadProfile".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.ZEN_3PH_DAILYLOADPROFILE;
		}
		else if ("ZEN_3ph_DeltaLoadProfile".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.ZEN_3PH_DELTALOADPROFILE;
		}
		else if ("ZEN_3ph_BillingData".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.ZEN_3PH_BILLINGDATA;
		}
		else if ("ZEN_3ph_VoltageRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.ZEN_3PH_VOLTAGERELATEDEVENTS;
		}
		else if ("ZEN_3ph_CurrentRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.ZEN_3PH_CURRENTRELATEDEVENTS;
		}
		else if ("ZEN_3ph_OtherRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.ZEN_3PH_OTHERRELATEDEVENTS;
		}
		else if ("HPL_1ph_InstantaneousRead".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.HPL_1PH_INSTANTANEOUSREAD;
		}
		else if ("HPL_1ph_DailyLoadProfile".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.HPL_1PH_DAILYLOADPROFILE;
		}
		else if ("HPL_1ph_DeltaLoadProfile".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.HPL_1PH_DELTALOADPROFILE;
		}
		else if ("HPL_1ph_BillingData".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.HPL_1PH_BILLINGDATA;
		}
		else if ("HPL_1ph_OtherRelatedEvents".equalsIgnoreCase(keyName)) {
			fieldName = DivFactorPropertyName.HPL_1PH_OTHERRELATEDEVENTS;
		}

		return fieldName;
	}

	public static class DivFactorPropertyName {
		public static final String IHM_1PH_INSTANTANEOUSREAD = "IHM 1Ph Instantaneous Read";
		public static final String IHM_3PH_INSTANTANEOUSREAD = "IHM 3Ph Instantaneous Read";
		public static final String IHM_1PH_DAILYLOADPROFILE = "IHM 1Ph Daily Load Profile";
		public static final String IHM_3PH_DAILYLOADPROFILE = "IHM 3Ph Daily Load Profile";
		public static final String IHM_1PH_DELTALOADPROFILE = "IHM 1Ph Delta Load Profile";
		public static final String IHM_3PH_DELTALOADPROFILE = "IHM 3Ph Delta Load Profile";
		public static final String IHM_1PH_BILLINGDATA = "IHM 1Ph Billing Data";
		public static final String IHM_3PH_BILLINGDATA = "IHM 3Ph Billing Data";
		public static final String IHM_1PH_CURRENTRELATEDEVENTS = "IHM 1Ph Current Related Events";
		public static final String IHM_3PH_CURRENTRELATEDEVENTS = "IHM 3Ph Current Related Events";
		public static final String IHM_1PH_OTHERRELATEDEVENTS = "IHM 1Ph Other Related Events";
		public static final String IHM_3PH_OTHERRELATEDEVENTS = "IHM 3Ph Other Related Events";
		public static final String IHM_3PH_VOLTAGERELATEDEVENTS = "IHM 3Ph Voltage Related Events";
		public static final String JPM_1PH_INSTANTANEOUSREAD = "JPM 1Ph Instantaneous Read";
		public static final String JPM_3PH_INSTANTANEOUSREAD = "JPM 3Ph Instantaneous Read";
		public static final String JPM_1PH_DAILYLOADPROFILE = "JPM 1Ph Daily Load Profile";
		public static final String JPM_3PH_DAILYLOADPROFILE = "JPM 3Ph Daily Load Profile";
		public static final String JPM_1PH_DELTALOADPROFILE = "JPM 1Ph Delta Load Profile";
		public static final String JPM_3PH_DELTALOADPROFILE = "JPM 3Ph delta Load Profile";
		public static final String JPM_1PH_BILLINGDATA = "JPM 1Ph Billing Data";
		public static final String JPM_3PH_BILLINGDATA = "JPM 3Ph Billing Data";
		public static final String JPM_1PH_CURRENTRELATEDEVENTS = "JPM 1Ph Current Related Events";
		public static final String JPM_3PH_CURRENTRELATEDEVENTS = "JPM 3Ph Current Related Events";
		public static final String JPM_1PH_OTHERRELATEDEVENTS = "JPM 1Ph Other Related Events";
		public static final String JPM_3PH_OTHERRELATEDEVENTS = "JPM 3Ph Other Related Events";
		public static final String JPM_3PH_VOLTAGERELATEDEVENTS = "JPM 3Ph Voltage Related Events";
		public static final String FLA_1PH_INSTANTANEOUSREAD = "FLA 1Ph Instantaneous Read";
		public static final String FLA_3PH_INSTANTANEOUSREAD = "FLA 3Ph Instantaneous Read";
		public static final String FLA_1PH_DAILYLOADPROFILE = "FLA 1Ph Daily Load Profile";
		public static final String FLA_3PH_DAILYLOADPROFILE = "FLA 3Ph Daily Load Profile";
		public static final String FLA_1PH_DELTALOADPROFILE = "FLA 1Ph Delta Load Profile";
		public static final String FLA_3PH_DELTALOADPROFILE = "FLA 3Ph Delta Load Profile";
		public static final String FLA_1PH_BILLINGDATA = "FLA 1Ph Billing Data";
		public static final String FLA_3PH_BILLINGDATA = "FLA 3Ph Billing Data";
		public static final String FLA_1PH_CURRENTRELATEDEVENTS = "FLA 1Ph Current Related Events";
		public static final String FLA_3PH_CURRENTRELATEDEVENTS = "FLA 3Ph Current Related Events";
		public static final String FLA_1PH_OTHERRELATEDEVENTS = "FLA 1Ph Other Related Events";
		public static final String FLA_3PH_OTHERRELATEDEVENTS = "FLA 3Ph Other Related Events";
		public static final String FLA_3PH_VOLTAGERELATEDEVENTS = "FLA 3Ph Voltage Related Events";
//		#VERSIONSPECIFICDIVISIONFACTOR  
		public static final String ALLIED_1PH_INSTANTANEOUSREAD = "ALLIED 1Ph Instantaneous Read";
		public static final String ALLIED_V5_62_1PH_INSTANTANEOUSREAD = "ALLIED V5.62 1Ph Instantaneous Read";
		public static final String ALLIED_AW3_30_1PH_INSTANTANEOUSREAD = "ALLIED AW3.30 1Ph Instantaneous Read";
		public static final String ALLIED_AW3_09_1PH_INSTANTANEOUSREAD = "ALLIED AW3.09 1Ph Instantaneous Read";
		public static final String ALLIED_AW3_31_1PH_INSTANTANEOUSREAD = "ALLIED AW3.31 1Ph Instantaneous Read";
		public static final String ALLIED_3PH_INSTANTANEOUSREAD = "ALLIED 3Ph Instantaneous Read";
		public static final String ALLIED_1PH_DAILYLOADPROFILE = "ALLIED 1Ph Daily Load Profile";
		public static final String ALLIED_AW3_31_1PH_DAILYLOADPROFILE = "ALLIED AW3.31 1Ph Daily Load Profile";
		public static final String ALLIED_V5_62_1PH_DAILYLOADPROFILE = "ALLIED V5.62 1Ph Daily Load Profile";
		public static final String ALLIED_3PH_DAILYLOADPROFILE = "ALLIED 3Ph Daily Load Profile";
		public static final String ALLIED_1PH_DELTALOADPROFILE = "ALLIED 1Ph Delta Load Profile";
		public static final String ALLIED_AW3_31_1PH_DELTALOADPROFILE = "ALLIED AW3.31 1Ph Delta Load Profile";
		public static final String ALLIED_V5_62_1PH_DELTALOADPROFILE = "ALLIED V5.62 1Ph Delta Load Profile";
		public static final String ALLIED_3PH_DELTALOADPROFILE = "ALLIED 3Ph Delta Load Profile";
		public static final String ALLIED_1PH_BILLINGDATA = "FLA 1Ph Billing Data";
		public static final String ALLIED_AW3_31_1PH_BILLINGDATA = "ALLIED AW3.31 1Ph Billing Data";
		public static final String ALLIED_V5_62_1PH_BILLINGDATA = "ALLIED V5.62 1Ph Billing Data";
		public static final String ALLIED_3PH_BILLINGDATA = "ALLIED 3Ph Billing Data";
		public static final String ALLIED_1PH_CURRENTRELATEDEVENTS = "ALLIED 1Ph Current Realated Events";
		public static final String ALLIED_V5_62_1PH_CURRENTRELATEDEVENTS = "ALLIED V5.62 Ph Current Related Events";
		public static final String ALLIED_3PH_CURRENTRELATEDEVENTS = "ALLIED 3Ph Current Related Events";
		public static final String ALLIED_1PH_OTHERRELATEDEVENTS = "ALLIED 1Ph Other Related Events";
		public static final String ALLIED_V5_62_1PH_OTHERRELATEDEVENTS = "ALLIED V5.62 1Ph Other Related Events";
		public static final String ALLIED_3PH_OTHERRELATEDEVENTS = "ALLIED 3Ph Other Related Events";
		public static final String ALLIED_3PH_VOLTAGERELATEDEVENTS = "ALLIED 3Ph Voltage Related Events";
		public static final String LECS_1PH_INSTANTANEOUSREAD = "LECS 1Ph Instantaneous Read";
		public static final String LECS_3PH_INSTANTANEOUSREAD = "LECS 3Ph Instantaneous Read";
		public static final String LECS_1PH_DAILYLOADPROFILE = "LECS 1Ph Daily Load Profile";
		public static final String LECS_3PH_DAILYLOADPROFILE = "LECS 3Ph Daily Load Profile";
		public static final String LECS_1PH_DELTALOADPROFILE = "LECS 1Ph Delta Load Profile";
		public static final String LECS_3PH_DELTALOADPROFILE = "LECS 3Ph Delta Load Profile";
		public static final String LECS_1PH_BILLINGDATA = "LECS 1Ph Billing Data";
		public static final String LECS_3PH_BILLINGDATA = "LECS 3Ph Billing Data";
		public static final String LECS_1PH_CURRENTRELATEDEVENTS = "LECS 1Ph Current Related Events";
		public static final String LECS_3PH_CURRENTRELATEDEVENTS = "LECS 3Ph Current Related Events";
		public static final String LECS_1PH_OTHERRELATEDEVENTS = "LECS 1Ph Other Related Events";
		public static final String LECS_3PH_OTHERRELATEDEVENTS = "LECS 3Ph Other Related Events";
		public static final String LECS_3PH_VOLTAGERELATEDEVENTS = "LECS 3Ph Voltage Related Events";
		public static final String CPS_1PH_INSTANTANEOUSREAD = "CPS 1Ph Instantaneous Read";
		public static final String CPS_3PH_INSTANTANEOUSREAD = "CPS 3Ph Instantaneous Read";
		public static final String CPS_1PH_DAILYLOADPROFILE = "CPS 1Ph Daily Load Profile";
		public static final String CPS_3PH_DAILYLOADPROFILE = "CPS 3Ph Daily Load Profile";
		public static final String CPS_1PH_DELTALOADPROFILE = "CPS 1Ph Delta Load Profile";
		public static final String CPS_3PH_DELTALOADPROFILE = "CPS 3Ph Delta Load Profile";
		public static final String CPS_1PH_BILLINGDATA = "CPS 1Ph Billing Data";
		public static final String CPS_3PH_BILLINGDATA = "CPS 3Ph Billing Data";
		public static final String CPS_1PH_CURRENTRELATEDEVENTS = "CPS 1Ph Current Related Events";
		public static final String CPS_3PH_CURRENTRELATEDEVENTS = "CPS 3Ph Current Related Events";
		public static final String CPS_1PH_OTHERRELATEDEVENTS = "CPS 1Ph Other Related Events";
		public static final String CPS_3PH_OTHERRELATEDEVENTS = "CPS 3Ph Other Related Events";
		public static final String CPS_3PH_VOLTAGERELATEDEVENTS = "CPS 3Ph Voltage Related Events";
		public static final String CPS_CT_INSTANTANEOUSREAD = "CPS CT Instantaneous Read";
		public static final String CPS_CT_DAILYLOADPROFILE = "CPS CT Daily Load Profile";
		public static final String CPS_CT_DELTALOADPROFILE = "CPS CT Delta Load Profile";
		public static final String CPS_CT_BILLINGDATA = "CPS CT Billing Data";
		public static final String CPS_CT_CURRENTRELATEDEVENTS = "CPS CT Current Related Events";
		public static final String CPS_CT_OTHERRELATEDEVENTS = "CPS CT Other Related Events";
		public static final String CPS_CT_VOLTAGERELATEDEVENTS = "CPS CT Voltage Related Events";
		public static final String ZEN_1PH_INSTANTANEOUSREAD = "ZEN 1Ph Instantaneous Read";
		public static final String ZEN_1PH_DAILYLOADPROFILE = "ZEN 1Ph Daily Load Profile";
		public static final String ZEN_1PH_DELTALOADPROFILE = "ZEN 1Ph Delta Load Profile";
		public static final String ZEN_1PH_BILLINGDATA = "ZEN 1Ph Billing Data";
		public static final String ZEN_1PH_VOLTAGERELATEDEVENTS = "ZEN 1Ph Voltage Related Events";
		public static final String ZEN_1PH_CURRENTRELATEDEVENTS = "ZEN 1Ph Current Realated Events";
		public static final String ZEN_1PH_OTHERRELATEDEVENTS = "ZEN 1Ph Other Related Events";
		public static final String ZEN_3PH_INSTANTANEOUSREAD = "ZEN 3Ph Instantaneous Read";
		public static final String ZEN_3PH_DAILYLOADPROFILE = "ZEN 3Ph Daily Load Profile";
		public static final String ZEN_3PH_DELTALOADPROFILE = "ZEN 3Ph Delta Load profile";
		public static final String ZEN_3PH_BILLINGDATA = "ZEN 3Ph Billing Data";
		public static final String ZEN_3PH_VOLTAGERELATEDEVENTS = "ZEN 3Ph Voltage Related Events";
		public static final String ZEN_3PH_CURRENTRELATEDEVENTS = "ZEN 3Ph Current Related Events";
		public static final String ZEN_3PH_OTHERRELATEDEVENTS = "ZEN 3Ph Other Related Events";
		public static final String HPL_1PH_INSTANTANEOUSREAD = "HPL 1Ph Instantaneous Read";
		public static final String HPL_1PH_DAILYLOADPROFILE = "HPL 1Ph Daily Load Profile";
		public static final String HPL_1PH_DELTALOADPROFILE = "HPL 1Ph Delta Load profile";
		public static final String HPL_1PH_BILLINGDATA = "HPL 1Ph Billing Data";
		public static final String HPL_1PH_OTHERRELATEDEVENTS = "HPL 1Ph Other Related Events";

		public static final String ENABLE = "Y";
		public static final String DISABLE = "N";
		public static final String PROPERTY_UPDATE_KEY = "meter.applicationProperties.update";

	}

}
