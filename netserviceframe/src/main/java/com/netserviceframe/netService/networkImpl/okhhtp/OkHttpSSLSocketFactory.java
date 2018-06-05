package com.netserviceframe.netService.networkImpl.okhhtp;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;


public class OkHttpSSLSocketFactory {


    public static javax.net.ssl.SSLSocketFactory getSSLSocketFactory(Context context, String name) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);


            InputStream ins = context.getAssets().open(name);
            keyStore.setCertificateEntry("0", certificateFactory.generateCertificate(ins));
            ins.close();

            SSLContext sslContext = SSLContext.getInstance("TLS");

            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            return sslContext.getSocketFactory();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 从assets中读取证书文件
     */
    private static Certificate getCertificateFromAssetsFile(Context context, String certName) {
        InputStream caInput = null;
        Certificate ca = null;
        try {
            caInput = context.getAssets().open(certName);
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            ca = cf.generateCertificate(caInput);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (caInput != null) {
                    caInput.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ca;
    }
}
