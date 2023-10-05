import java.util.Scanner;
public class Geomatica3 {
  public static void main(String[] args) {
    Scanner entrada =new Scanner(System.in);

    //Geodesicas a UTM

    double semiEjeMayor=6378137;
    double primExcentricidad=0.00669438;
    double segExcentricidad=0.006739497;
    double valorAsub0=0.9983242985;
    double valorAsub2=0.002514607064;
    double valorAsub4=0.000002639;
    double valorAsub6=0.00000000341805;
    double valorMeridianoCentral=0.9996;
    System.out.println("****** Bienvenido *****");
    System.out.println("De Geodesicas a UTM");
    System.out.println("Ingrese Coordenadas Geodesicas");

    System.out.println("Ingrese LATITUD, separado por espacios: ");
    double graLatitud = entrada.nextDouble();
    double minLatitud = entrada.nextDouble();
    double segLatitud = entrada.nextDouble();

    double latGrad = graLatitud;
    double latMin = minLatitud / 60;
    double latSeg = segLatitud / 3600;
    double numNatLat = latGrad + latMin +latSeg;
    double lat;

    System.out.println("Ingrese (N)Latitud NORTE o (S)Latitud SUR?: ");
      String latitud = entrada.next();
        if (latitud.equalsIgnoreCase("s"))
          {lat=numNatLat*-1;}
        else
          {lat=numNatLat*1;}

        System.out.println("Ingrese la Altura ElipsoidalL: ");
        double altElipsoidal = entrada.nextDouble();

        System.out.println(" Ingrese LONGITUD, separado por espacios:  ");
         double gradLong = entrada.nextDouble();;
        double minLong = entrada.nextDouble();
        double segLong = entrada.nextDouble();

        double longGrad = gradLong;

        double longMin = minLong / 60;
        double longSeg = segLong / 3600;
        double numNatLong = longGrad + longMin + longSeg;
        double lon;

        System.out.println("Ingrese (E)Longitud ESTE o (O)Logitud OESTE?: ");
            String longitud =entrada.next();
        if (longitud.equalsIgnoreCase("o"))
            {lon=numNatLong*-1;}
        else
            {lon=numNatLong*1;}

        //CALCULO DE PARAMETROS

        double zonaUtm=((lon)/6)+31;

        //Solo la parte entera
        double meridianoZona=(((int)zonaUtm)*6)-183;

        //Sin redondear a 9 decimales
        double valorT=Math.tan(lat*Math.PI/180);

        double nCuadrado=segExcentricidad*(Math.pow
                (Math.cos(lat*Math.PI/180), 2));

        double deltaLong=(((lon)-meridianoZona)/180)*Math.PI;

        double curVert=semiEjeMayor/
                (Math.sqrt(1-primExcentricidad*
                Math.pow(Math.sin(lat*Math.PI/180), 2)));

        //Calculo de la coordenada ESTE
        double  semiEsteA=deltaLong*(Math.cos
                (lat*Math.PI/180))*curVert;

        double semiEsteB=
                ((Math.pow(deltaLong * (Math.cos(lat * Math.PI / 180)), 3)
                * curVert * (1 - Math.pow(valorT, 2))) + nCuadrado) / 6;

        double semiEsteC=
                (Math.pow(deltaLong*(Math.cos(lat*Math.PI/180)),5)*
                curVert*(5-18*valorT+Math.pow(valorT,4))/120);

        double semiEsteD=semiEsteA+semiEsteB+semiEsteC;

        double esteE=500000+(valorMeridianoCentral*semiEsteD);

        System.out.println("La Coordenada UTM Este  es: " + esteE);

        //Calculo de la coordenada NORTE
        double semNorteA=semiEjeMayor*(valorAsub0*(lat*Math.PI/180)-
                valorAsub2*(Math.sin(2*(lat*Math.PI/180)))+
                valorAsub4*(Math.sin(4*(lat*Math.PI/180)))-
                valorAsub6*(Math.sin(6*(Math.PI/180))));
        double semiNorteB=
                semNorteA+(((Math.pow(deltaLong*(Math.cos(lat*Math.PI/180)), 2)*
                curVert*valorT)/2)+ (Math.pow(deltaLong*(Math.cos(lat*Math.PI/180)), 4)*
                        curVert*valorT*(5-(Math.pow(valorT,2))+9*nCuadrado+4*
                        (Math.pow(nCuadrado, 2)))/24)+
                        (Math.pow(deltaLong*(Math.cos(lat*Math.PI/180)), 6)*
                        curVert*valorT*(61-58*(Math.pow(valorT, 2))+Math.pow(valorT, 4))/720));

        double norteN=10000000+(valorMeridianoCentral*semiNorteB);

        System.out.println("La Coordenada UTM Norte es: "+norteN);
    }
}
