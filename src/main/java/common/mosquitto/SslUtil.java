package common.mosquitto;

import java.io.*;
import java.nio.file.*;
import java.security.*;
import java.security.cert.*;
import javax.net.ssl.*;

import org.bouncycastle.jce.provider.*;
import org.bouncycastle.openssl.*;
/**
 * from https://gist.github.com/liangzhenghui/8ed5559a757d5dbafe6bee5d45f758d2
 *  @date   2018年2月9日
 *	@author liangzhenghui
 */
public class SslUtil
{
	public static SSLSocketFactory sSLSocketFactory;
	public static SSLSocketFactory getSocketFactory(){
		if(sSLSocketFactory==null){
			init();
		}
		System.out.println("-----------------------------------"+sSLSocketFactory);;
		return sSLSocketFactory;
	}
	static {
		init();
	}
	public static void init(){
		try{
			//local   test
			final String caCrtFile="D:\\liangzhenghui\\fsl\\target\\classes\\ca.crt";
			final String crtFile="D:\\liangzhenghui\\fsl\\target\\classes\\client.crt";
		    final String keyFile="D:\\liangzhenghui\\fsl\\target\\classes\\client.key";
		    final String password="85574999";
			Security.addProvider(new BouncyCastleProvider());
	
			// load CA certificate
			PEMReader reader = new PEMReader(new InputStreamReader(new ByteArrayInputStream(Files.readAllBytes(Paths.get(caCrtFile)))));
			X509Certificate caCert = (X509Certificate)reader.readObject();
			reader.close();
	
			// load client certificate
			reader = new PEMReader(new InputStreamReader(new ByteArrayInputStream(Files.readAllBytes(Paths.get(crtFile)))));
			X509Certificate cert = (X509Certificate)reader.readObject();
			reader.close();
	
			// load client private key
			reader = new PEMReader(
					new InputStreamReader(new ByteArrayInputStream(Files.readAllBytes(Paths.get(keyFile)))),
					new PasswordFinder() {
						@Override
						public char[] getPassword() {
							return password.toCharArray();
						}
					}
			);
			KeyPair key = (KeyPair)reader.readObject();
			reader.close();
	
			// CA certificate is used to authenticate server
			KeyStore caKs = KeyStore.getInstance(KeyStore.getDefaultType());
			caKs.load(null, null);
			caKs.setCertificateEntry("ca-certificate", caCert);
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(caKs);
	
			// client key and certificates are sent to server so it can authenticate us
			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
			ks.load(null, null);
			ks.setCertificateEntry("certificate", cert);
			ks.setKeyEntry("private-key", key.getPrivate(), password.toCharArray(), new java.security.cert.Certificate[]{cert});
			KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(ks, password.toCharArray());
	
			// finally, create SSL socket factory
			SSLContext context = SSLContext.getInstance("TLSv1");
			context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
			sSLSocketFactory=context.getSocketFactory();
			System.out.println("init SSLSocketFactory");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
