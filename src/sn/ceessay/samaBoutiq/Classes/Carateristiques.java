
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.ceessay.samaBoutiq.Classes;

/**
 *
 * @author ceessay
 */
public class Carateristiques {

    private Double cadenceCPU = 0.0;
    private  Integer idCarac;
    private  String os;
    private  String marqueCPU;
    private  String archiCPU;
    private  Integer tailleRam;
    private  String tailleEcran;
    private  Integer taillDD;
    private  boolean wifi;
    private  boolean bluetooth;
    private  boolean webcam;
    private  boolean camera;
    private  String resolutionWeb;
    private  String resolutionCam;
    private  Integer idArticle;

    public Carateristiques(Integer idCarac, String os, String marqueCPU, Double cadenceCPU, String archiCPU,
            Integer tailleRam, Integer taillDD, String tailleEcran, boolean wifi, boolean bluetooth,
            boolean webcam,String resolutionWeb, boolean camera,  String resolutionCam,
            Integer idArticle) {
        
        this.idCarac = idCarac;
        this.os = os;
        this.marqueCPU = marqueCPU;
        this.cadenceCPU = cadenceCPU;
        this.archiCPU = archiCPU;
        this.tailleRam = tailleRam;
        this.tailleEcran = tailleEcran;
        this.taillDD = taillDD;
        this.wifi = wifi;
        this.bluetooth = bluetooth;
        this.webcam = webcam;
        this.camera = camera;
        this.resolutionWeb = resolutionWeb;
        this.resolutionCam = resolutionCam;
        this.idArticle = idArticle;
    }

    public Integer getIdCarac() {
        return idCarac;
    }

    public String getOs() {
        return os;
    }

    public String getMarqueCPU() {
        return marqueCPU;
    }

    public Double getCadenceCPU() {
        return cadenceCPU;
    }

    public String getArchiCPU() {
        return archiCPU;
    }

    public Integer getTailleRam() {
        return tailleRam;
    }

    public String getTailleEcran() {
        return tailleEcran;
    }

    public Integer getTaillDD() {
        return taillDD;
    }

    public boolean isWifi() {
        return wifi;
    }

    public boolean isBluetooth() {
        return bluetooth;
    }

    public boolean isWebcam() {
        return webcam;
    }

    public boolean isCamera() {
        return camera;
    }

    public String getResolutionWeb() {
       // System.out.println(isWebcam() +":\n "+resolutionWeb);
        return resolutionWeb;
    }

    public String getResolutionCam() {
        return resolutionCam;
    }

    public Integer getIdArticle() {
        return idArticle;
    }

    public void afficherCarac() {
        System.out.println(this.os + " \n" + this.archiCPU);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
