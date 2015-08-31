package com.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.common.Global;
import com.common.util.wechart.XMLParse;

import net.sf.json.JSONObject;

public class WeChatUtil {

	/*
	 * 微信的身份验证
	 */
	public static void check(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 随机字符串
		String echostr = request.getParameter("echostr");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		
		if(echostr==null || echostr.isEmpty()){
			response.getWriter().print(responseMsg(request,response));
		}
		else{
			if (!signature.isEmpty() && !timestamp.isEmpty() && !nonce.isEmpty() && !echostr.isEmpty()) {
				boolean res = checkSignature(signature, Global.TOKEN, timestamp, nonce);
				if (res) {
					response.getWriter().print(echostr);
				}
			}
		}
	}

	/*
	 * 通过code值，向微信服务器发送get请求获得openId
	 */
	@SuppressWarnings("deprecation")
	public static String httpGetOpenId(String code) {
		String openId = "";
		DefaultHttpClient client = new DefaultHttpClient();
		try {
			// 发送get请求
			String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Global.AppID + "&secret="
					+ Global.AppSecret + "&code=" + code + "&grant_type=authorization_code";
			
			//url="https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=jGfKbFS2t3pFsHDFYX4I2tP-kBwsurVeCTRP-98EURb_oGAVokRnIzz9tswEaetG5DnYneDhTtETaKwfelhbDM1zrK-j2ShP_ecLK5s0L90";
			//url="https://www.baidu.com/s?wd=java%20&rsv_spt=1&issp=1&f=8&rsv_bp=1&rsv_idx=2&ie=utf-8&tn=baiduhome_pg&rsv_enter=0&oq=java&rsv_t=525fKiwYK2OemfnyZBwSl4TqJe2Ijg1TPcZ9JTgrRjkcP6dq5AHOHtI1vkzcz2dZrU8S&rsv_pq=fd2e333d000081ee&rsv_sug=2";
			/*try {
	            System.out.println(url);
		          //  key = URLEncoder.encode(key, "UTF-8");
		            URL u = new URL(url);
		            URLConnection conn = u.openConnection();
		            BufferedReader reader = new BufferedReader(new InputStreamReader(
		                    conn.getInputStream(), "UTF-8"));
		            String str = reader.readLine();
		            while (str != null) {
		                System.out.println(str);
		                str = reader.readLine();
		            }
		            reader.close();
		            
		            
		        } catch (Exception ex) {
		            ex.printStackTrace();
		        }*/
			
			
			//url="https://www.baidu.com/s?wd=java%20&rsv_spt=1&issp=1&f=8&rsv_bp=1&rsv_idx=2&ie=utf-8&tn=baiduhome_pg&rsv_enter=0&oq=java&rsv_t=525fKiwYK2OemfnyZBwSl4TqJe2Ijg1TPcZ9JTgrRjkcP6dq5AHOHtI1vkzcz2dZrU8S&rsv_pq=fd2e333d000081ee&rsv_sug=2";
			HttpGet request = new HttpGet(url);
			HttpResponse response = client.execute(request);

			/** 请求发送成功，并得到响应 **/
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				/** 读取服务器返回过来的json字符串数据 **/
				String strResult = "{\"openid\":}";
				strResult = EntityUtils.toString(response.getEntity());
				System.out.println(strResult);
				/** 把json字符串转换成json对象 **/
				JSONObject jsonResult = JSONObject.fromObject(strResult);
				if(Integer.parseInt(jsonResult.getString("errcode"))==0){
					openId = jsonResult.getString("openid");
				}
				else{
					System.out.println("所给的code不正确");					
				}
			} else {
				System.out.println("get请求提交失败:" + url);
			}
		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return openId;
	}

	/*private static boolean checkSignature(String signature, String token, String timestamp, String nonce) {
		String[] tmpArr = { token, timestamp, nonce };
		Arrays.sort(tmpArr);
		if (tmpArr.toString().equals(signature)) {
			return true;
		} else {
			return false;
		}
	}*/

	public static String responseMsg(HttpServletRequest request, HttpServletResponse response) {
		// ---------- 接 收 数 据 ---------- //
		Map<String, String> map = XMLParse.extract(request);
		String res = "";
		if (map.get("MsgType").equals("event")) {
			res = receiveEvent(map);
		} else {
			String contentStr = "有问题请向人力部进行详细咨询"; // 返回消息内容
			res = transmitText(map, contentStr);
		}
		return res;
	}

	/*
	 * 根据推送事件作出相应的回复
	 */
	private static String receiveEvent(Map<String, String> map) {
		String res = "";
		switch (map.get("Event")) {
		case "subscribe":// 关注事件
			res = "欢迎关注中移物联网员工服务号，在进行其他的操作之前，请先点击“浏览藏书”、“借阅记录”和“预约办理”进行账号的绑定";
			break;
		case "unsubscribe": // 取消关注事件
			res = "你已经取消了关注，欢迎再次进行关注O(∩_∩)O~";
			break;
		case "CLICK":
			String eKey = map.get("EventKey");
			if (eKey.equals("B001")) {
				// 这里需要从数据中查询出好书推荐
				res = "《诛仙》这本书写得很不错！";
			} else if (eKey.equals("B002")) {
				// 这里需要从数据库中进行查询，获得新书推荐
				res = "购买了新书《西游记》，大家有时间可以去看看";
			} else if (eKey.equals("S001")) {
				// 这里需要跳转到员工福利的页面
				res = "员工福利1/2/3 /(ㄒoㄒ)/~~";
			} else if (eKey.equals("S002")) {
				// 这里需要跳转到相关政策的页面
				res = "正在商定政策，尽请期待O(∩_∩)O~";
			}
			break;
		}
		return transmitText(map, res);
	}

	private static String transmitText(Map<String, String> map, String contentStr) {
		// 返回消息模板
		String textTpl = "<xml>" + "<ToUserName><![CDATA[%s]]></ToUserName>  "
				+ "<FromUserName><![CDATA[%s]]></FromUserName>  " + "<CreateTime>%s</CreateTime>  "
				+ "<MsgType><![CDATA[%s]]></MsgType>  " + "<Content><![CDATA[%s]]></Content>  "
				+ "<FuncFlag>0</FuncFlag>  " + "</xml>";
		String msgType = "text";
		return String.format(textTpl, map.get("FromUserName"), map.get("ToUserName"), System.currentTimeMillis(),
				msgType, contentStr);

	}
	
	
	/**
     * 验证签名
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
	private static boolean checkSignature(String signature, String token, String timestamp, String nonce) {
	    String[] arr = new String[]{token, timestamp, nonce};
        // 将 token, timestamp, nonce 三个参数进行字典排序
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for(int i = 0; i < arr.length; i++){
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;         
        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行 shal 加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        content = null;
        // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()): false;
    }
     
    /**
     * 将字节数组转换为十六进制字符串
     * @param digest
     * @return
     */
    private static String byteToStr(byte[] digest) {
        // TODO Auto-generated method stub
        String strDigest = "";
        for(int i = 0; i < digest.length; i++){
            strDigest += byteToHexStr(digest[i]);
        }
        return strDigest;
    }
     
    /**
     * 将字节转换为十六进制字符串
     * @param b
     * @return
     */
    private static String byteToHexStr(byte b) {
        // TODO Auto-generated method stub
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(b >>> 4) & 0X0F];
        tempArr[1] = Digit[b & 0X0F];
         
        String s = new String(tempArr);
        return s;
    }
	
}
