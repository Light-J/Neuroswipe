package com.nsa.cubric.application.domain;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import com.itextpdf.text.Document;

public class Certificate {
    private String email;


    public Certificate(String email){
        this.email = email;
    }

    public ByteArrayInputStream generateCertificatePDF(){

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();


    }

}
