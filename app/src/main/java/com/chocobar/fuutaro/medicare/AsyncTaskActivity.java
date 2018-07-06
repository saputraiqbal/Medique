package com.chocobar.fuutaro.medicare;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class AsyncTaskActivity {
    public static List<Object> doAsyncTask(String varName, String paramValue){
        List<Object> dataSent = new ArrayList<Object>();

        //initializing the process of request first
        SoapObject reqsWebSvc = new SoapObject(STATIC_VALUES.NAMESPACE, "CallSpExcecution");

        //configuring the envelope before creating it
        PropertyInfo SPNameInfo = new PropertyInfo();
        SPNameInfo.setNamespace(STATIC_VALUES.NAMESPACE);
        SPNameInfo.setType(PropertyInfo.STRING_CLASS);
        SPNameInfo.setName("txtSPName");
        SPNameInfo.setValue(varName);
        reqsWebSvc.addProperty(SPNameInfo);

        PropertyInfo ParamValueInfo = new PropertyInfo();
        ParamValueInfo.setNamespace(STATIC_VALUES.NAMESPACE);
        ParamValueInfo.setType(PropertyInfo.STRING_CLASS);
        ParamValueInfo.setName("txtParamValue");
        ParamValueInfo.setValue(paramValue);
        reqsWebSvc.addProperty(ParamValueInfo);

        //creating an envelope
        SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        env.setOutputSoapObject(reqsWebSvc);
        env.dotNet = true;

        //creating the request
        HttpTransportSE httpTrans = new HttpTransportSE(STATIC_VALUES.URL);

        dataSent.add(env);
        dataSent.add(httpTrans);

        return dataSent;
    }
}
