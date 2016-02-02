/** 
 * 使用DES加密與解密,可對byte[],String類型進行加密與解密 密文可使用String,byte[]存儲.
 * 方法:
 * void setEncryptKey(String strKey)    從strKey的字條生成一個Key
 * String getEncString(String strMing)  對strMing進行加密,返回String密文
 * String getDesString(String strMi)    對strMin進行解密,返回String明文
 * byte[] getEncCode(byte[] byteS)      byte[]型的加密
 * byte[] getDesCode(byte[] byteD)      byte[]型的解密
 */
package TravelApply;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DesEncrypt {
    public DesEncrypt(String encryptKey){
        this.setEncryptKey(encryptKey);
    }

    private Key key;

    /**
     * 根據參數生成KEY
     * @param strKey
     */
    public void setEncryptKey(String strKey) {
        try {
            KeyGenerator _generator = KeyGenerator.getInstance("DES");
            _generator.init(new SecureRandom(strKey.getBytes()));
            this.key = _generator.generateKey();
            _generator = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加密String明文輸入,String密文輸出
     * @param strMing
     * @return
     */
    public String getEncString(String strMing) {
        byte[] byteMi = null;
        byte[] byteMing = null;
        String strMi = "";
        Base64 base64 = new Base64();
        try {
            byteMing = strMing.getBytes("UTF8");
            byteMi = this.getEncCode(byteMing);
            strMi = new String(base64.encodeBase64(byteMi));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            base64 = null;
            byteMing = null;
            byteMi = null;
        }
        return strMi;
    }
  
    /**
     * 解密 以String密文輸入,String明文輸出
     * @param strMi
     * @return
     */
    public String getDesString(String strMi) {
        Base64 base64 = new Base64();
        byte[] byteMing = null;
        byte[] byteMi = null;
        String strMing = "";
        try {
            byteMi = base64.decodeBase64(strMi.getBytes("UTF8"));
            byteMing = this.getDesCode(byteMi);
            strMing = new String(byteMing, "UTF8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            base64 = null;
            byteMing = null;
            byteMi = null;
        }
        return strMing;
    }
  
    /**
     * 加密以byte[]明文輸入,byte[]密文輸出
     * @param byteS
     * @return
     */
    private byte[] getEncCode(byte[] byteS) {
        byte[] byteFina = null;
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byteFina = cipher.doFinal(byteS);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cipher = null;
        }
        return byteFina;
    }  
  
    /**
     * 解密以byte[]密文輸入,以byte[]明文輸出
     * @param byteD
     * @return
     */
    private byte[] getDesCode(byte[] byteD) {
        Cipher cipher;
        byte[] byteFina = null;
        try {
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byteFina = cipher.doFinal(byteD);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cipher = null;
        }
        return byteFina;
    }
}
