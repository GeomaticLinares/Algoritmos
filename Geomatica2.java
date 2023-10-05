import java.util.Scanner;
public class Geomatica2 {
  public static void main(String[] args) {
    Scanner entrada =new Scanner(System.in);

    //Cartesianas a Geodesicas

    double semiEjeMayor = 6378137;
    double semiEjeMenor = 6356752.314;
    double primExcentricidad = 0.00669438;
    double segExcentricidad = 0.006739497;
    System.out.println("***** Bienvenido *****");
    System.out.println("*De Geocentricas a Geodesicas*");

    System.out.println("INGRESE LA COORDENADA GEOCENTRICA X: ");
    double coordX=entrada.nextDouble();
    System.out.println("INGRESE LA COORDENADA GEOCENTRICA Y: ");
    double coordY=entrada.nextDouble();
    System.out.println("INGRESE LA ALTURA GEOCENTRICA Z: ");
    double alturaZ=entrada.nextDouble();

    //CALCULO DEL VALOR DE P
    double valorP=Math.sqrt(Math.pow(coordX,2)+Math.pow(coordY,2));

    //CALCULO DEL VALOR DE TETTA
    double valorTeta=Math.atan(((alturaZ*semiEjeMayor)/
        (valorP*semiEjeMenor)));

    //CALCULO DE LA LATITUD
    double phi=Math.atan((alturaZ+(segExcentricidad*semiEjeMenor*
            Math.pow(Math.sin(valorTeta), 3)))
            /(valorP-(primExcentricidad*semiEjeMayor*
            Math.pow(Math.cos(valorTeta), 3))) );

    System.out.println("****Latitud****");
    double grados=(Math.toDegrees(phi)-(Math.toDegrees(phi)%1));

    double minutos;
    if(grados<0)
    {minutos=(Math.toDegrees(phi)%1)*-60;}
    else
    {minutos=(Math.toDegrees(phi)%1)*60;}

    //Convirtiendo a Segundos
    double segundos=(minutos%1)*60;

    System.out.println(+(int)grados+"°"+" "+(int)minutos+"´"+" "
            +String.format("%.5f",segundos)+" ");

    //CALCULO DE LA LONGITUD
    double lamda=Math.atan(coordY/coordX);

    System.out.println("****Longitud****");
    grados=(Math.toDegrees(lamda)-(Math.toDegrees(lamda)%1));
    //System.out.println("GRADOS: "+(int)grados)

    if(grados<0)
    {minutos=(Math.toDegrees(lamda)%1)*-60;}
    else
    {minutos=(Math.toDegrees(lamda)%1)*60;}

    //convirtiendo a segundos
    segundos=(minutos%1)*60;

    System.out.println(+(int)grados+"°"+" "+(int)minutos+"´"+" "
            +String.format("%.5f",segundos)+" ");

    //CALCULANDO EL RADIO DE CURVATURA DE LA PRIMERA VERTICAL
    double curVet=semiEjeMayor/(Math.sqrt(1-(primExcentricidad*
            Math.pow(Math.sin(phi), 2))));
    System.out.println("VERTICAL PRIMA: "+curVet);
    System.out.println("VERTICAL PRIMA: "+String.format("%.3f",curVet));

    //CALCULANDO LA ALTURA ELIPSOIDAL
    double altElipsoidal=(valorP/Math.cos(phi))-curVet;
    System.out.println("ALTURA ELIPSOIDAL: "
            +String.format("%.4f",altElipsoidal));

  }
}