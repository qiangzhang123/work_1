package bbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;

import utils.RSAUtils;
import utils.SHA1Util;
import utils.ThirdUtil;

@SuppressWarnings("serial")
public class GenUrl extends JFrame{

	public static void main(String[] args) throws Exception {
		Data data = getData();
		//平安分配公钥
	    String publicKey = "MIGiMA0GCSqGSIb3DQEBAQUAA4GQADCBjAKBhACGeGMZ03Z7dMDgU7CcqN7Omlto1wEg+Y6g5ZvTzplhXOHSmtkyG3b3wYVg/aQeyWt2A6r7mbLaUx9TWDIdG/gKRluR7egYY1/3Ql0yp40XFn5MLXmEKXUS9th8IvdwL2KJU7sYNpR4cQ7LT21F/E6ejsUQ9DGyr7unNE4Hfk6eRCoVvQIDAQAB";
		//平安分配加签key
	    String sha1Key = "b34dc091889949cc86e85978355756b4";
		//平安商户id
		String mchId = data.getMchId();
		String state = data.getState();
		//待加签参数，没有值则不参与加签
		Map<String,String> treeMap = new TreeMap<String,String>();
		//卡号
		if (StringUtils.isNotEmpty(data.getAccNo())) {
			treeMap.put("accNo", data.getAccNo());
		}
		//证件号码
		if (StringUtils.isNotEmpty(data.getClientIdNo())) {
			treeMap.put("clientIdNo", data.getClientIdNo());
		}
		//证件姓名
		if (StringUtils.isNotEmpty(data.getClientName())) {
			treeMap.put("clientName", data.getClientName());
		}
		if (StringUtils.isNotEmpty(data.getClientIdType())) {
			//证件类型
			treeMap.put("clientIdType", data.getClientIdType());
		}
		//手机号
		if (StringUtils.isNotEmpty(data.getTelNo())) {
			treeMap.put("telNo", data.getTelNo());
		}
		//合作方会员id
		treeMap.put("thirdMid", data.getThirdMid());
		//时间戳
		treeMap.put("timestamp", String.valueOf(new Date().getTime()));
		
		//组装参数  a=a&b=b&c=c
		StringBuilder sb = new StringBuilder();
		for (Entry<String, String> entry:treeMap.entrySet()) {
			sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		String urlStr = sb.toString();
		urlStr = urlStr.substring(0,urlStr.lastIndexOf("&"));
		//参数加签
		String signature = SHA1Util.sha1(urlStr.toLowerCase() + sha1Key);
		String predata = urlStr + "&signature="+signature;
		System.out.println("predata:" + predata);
		String redirectUrl = "";
		if (StringUtils.isEmpty(data.getRedirectUrl())) {
			redirectUrl = ThirdUtil.urlEncode("https://bank-static-stg.pingan.com.cn/bbc/index/landing.html");
		} else {
			redirectUrl = ThirdUtil.urlEncode(data.getRedirectUrl());
		}
		String encryptData = ThirdUtil.bytesToHexString(RSAUtils.encryptByPublicKey(ThirdUtil.getBytes(predata), publicKey));
		String URL = "https://rmb-stg.pingan.com.cn/brcp/uc/cust/uc-third-auth.autoLogin.do?mchId=" +  mchId +  "&encryptData=" + encryptData+"&redirectUrl=" + redirectUrl + "&state=" +ThirdUtil.urlEncode(state);
		//生成免登URL
		System.out.println("URL:" + URL );
		JTextArea jt =new JTextArea("免登地址", 20, 20);
		jt.setText(URL);
		jt.setLineWrap(true);        //激活自动换行功能 
		jt.setWrapStyleWord(true);    
		JOptionPane.showMessageDialog(null,jt);
	}
	
	private static Data getData() throws IOException{
		Data data = new Data();
		Properties properties = new Properties();
		FileInputStream fis = new FileInputStream("src/config.properties");
		InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
		properties.load(isr);
		data.setMchId((String)properties.get("mchId"));
		data.setAccNo((String)properties.get("accNo"));
		data.setClientIdNo((String)properties.get("clientIdNo"));
		data.setClientName((String)properties.get("clientName"));
		data.setClientIdType((String)properties.get("clientIdType"));
		data.setTelNo((String)properties.get("telNo"));
		data.setThirdMid((String)properties.get("thirdMid"));
		data.setState((String)properties.get("state"));
		data.setRedirectUrl((String)properties.get("redirectUrl"));
		isr.close();
		if (null !=fis ) {
			fis.close();
		}
		if (StringUtils.isEmpty(data.getMchId())) {
			throw new IllegalArgumentException("mchId 不能空");
		}
		if (StringUtils.isEmpty(data.getThirdMid())) {
			throw new IllegalArgumentException("thirdMid 不能空");
		}
		System.out.println("数据：" + JSON.toJSONString(data));
		return data;
	}
}
