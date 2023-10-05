import java.util.Scanner;
public class Geomatica8 {
  public static void main(String[] args) {
    Scanner entrada= new Scanner(System.in);

    //UTM a Topograficas

    System.out.println("Bienvenido: De UTM a Topograficas");
    System.out.println(" Ingrese UTM del Pto A, Seperado por espacios: ");
    System.out.println("Ejemplo: 422849.015   8440895.056");
    double coordEsteA = entrada.nextDouble();
    double coordNorteA = entrada.nextDouble();

    System.out.println("LATITUD del Pto A, Separado por espacios: ");
    System.out.println("Ejemplo: -04 35 13.68365");
    double gradLatitudA = entrada.nextDouble();
    double minLatitudA = entrada.nextDouble();
    double segLatitudA = entrada.nextDouble ();

    double latGradA = gradLatitudA;
    double latMinA = minLatitudA/60;
    double latSegA = segLatitudA/3600;
    double numNatLatA = latGradA+latMinA+latSegA;

    System.out.println("Alt. Elipsoidal del Pto A: ");
    double altElipsoidalA = entrada.nextDouble();

    System.out.print("UTM del Pto B, Separado por espacios: ");
    System.out.println("Ejemplo: 426728.246   8439616.840");
    double coordEsteB = entrada.nextDouble();
    double coordNorteB = entrada.nextDouble();

    System.out.println("LATITUD del Pto B, Separado por espacios :");
    System.out.println("Ejemplo: -12 03 42.55680");
    double gradLatitudB = entrada.nextDouble();
    double minLatitudB = entrada.nextDouble();
    double segLatitudB = entrada.nextDouble ();

    double latGradB = gradLatitudB;
    double latMinB = minLatitudB/60;
    double latSegB = segLatitudB/3600;
    double numNatLatB = latGradB+latMinB+latSegB;

    System.out.print("Alt. Elipsoidal del Pto B: ");
    double altElipsoidalB = entrada.nextDouble();

    //Longitud Proyectada
    double longProyectada =
        Math.sqrt(Math.pow(coordNorteB-coordNorteA,2)+
        (Math.pow(coordEsteB-coordEsteA,2)));
    System.out.println("Longitud Proyectada:"+longProyectada);

    //Factor de Escala de A
    double valorXa = (500000 - coordEsteA);
    double valorQa = (0.000001 * valorXa);
    double curVertA = 6378137/
        (Math.sqrt(1-0.00669438*Math.pow(Math.sin((numNatLatA*Math.PI/180)),2)));

    double valorPa1=
        1+0.006739497 * Math.pow(Math.cos(numNatLatA*Math.PI/180),2);
    double valorPa2 = 2*(Math.pow(curVertA,2))*(Math.pow(0.9996,2));
    double valorPa = valorPa1/valorPa2* Math.pow(10,12);

    double factEscalaA = 0.9996*(1+valorPa*(Math.pow(valorQa,2)+
        0.00003*(Math.pow(valorQa,4))));
    System.out.println("Factor de Escala de A: "+factEscalaA);

    //Factor de Escala de B
    double valorXb = (500000 - coordEsteB);
    double valorQb = (0.000001 * valorXb);

    double curVertB = 6378137/
        (Math.sqrt(1-0.00669438*Math.pow(Math.sin((numNatLatB*Math.PI/180)),2)));

    double valorPa3= 1+0.006739497 *
        Math.pow(Math.cos(numNatLatB*Math.PI/180),2);
    double valorPa4 = 2*(Math.pow(curVertB,2))*(Math.pow(0.9996,2));
    double valorPb = valorPa3/valorPa4* Math.pow(10,12);

    double factEscalaB = 0.9996*(1+valorPb*(Math.pow(valorQb,2)+
        0.00003*(Math.pow(valorQb,4))));
    System.out.println("Factor de Escala de B: "+factEscalaB);

    //factor de Escala Promedio de AyB
    double factEscalaProm = (factEscalaA+factEscalaB)/2;
    System.out.println("Factor de Escala de AyB: "+factEscalaProm);

    //Distancia Geodesica
    double distGeodesica = (longProyectada/factEscalaProm);
    System.out.println("Distancia Geodesica: "+distGeodesica);

    //Radio de Curvatura del Meridiano de AyB
    double mA = (6378137*(1-0.00669438))/
        Math.pow(1-0.00669438*Math.pow(Math.sin(numNatLatA*Math.PI/180),2),1.5);
    System.out.println("Radio del meridiano A: "+mA);
    double mB = (6378137*(1-0.00669438))/
        Math.pow(1-0.00669438*Math.pow(Math.sin(numNatLatB*Math.PI/180),2),1.5);
    System.out.println("Radio del meridiano B: "+mB);

    //Factor de Elevacion de AyB
    double factElevA = (mA/(mA+altElipsoidalA));
    System.out.println("Factor de Elevacion A: "+factElevA);
    double factElevB = (mB/(mB+altElipsoidalB));
    System.out.println("Factor de Elevacion B: "+factElevB);

    //Factor de Elevacion Promedio
    double factElevProm = (factElevA+factElevB) /2;
    System.out.println("Factor de Elevacion Promedio: "+factElevProm);

    //Distancia Topografica
    double distTopograf = (distGeodesica/factElevProm);
    System.out.println("Distancia Topograf: "+distTopograf);

    //Factor Combinado
    double factCombinado = (factElevProm*factEscalaProm);
    System.out.println("Factor Combinado: "+factCombinado);

    //Distancia Topografica
    double distTopografica = (longProyectada/factCombinado);
    System.out.println("Distancia Topografica: "+distTopografica);

    //Correccion de Azimut por Curvatura
    double deltaNorte = (coordNorteB-coordNorteA);
    System.out.println("Delta del Norte: "+deltaNorte);
    double x1 = 500000-coordEsteB;
    System.out.println("X1: "+x1);
    double x2 = 500000-coordEsteA;
    System.out.println("X2: "+x2);
    double corrAzimutPorCurv =
        (deltaNorte*(2*x1+x2)*valorPb*0.000000068755)/3600;
    System.out.println("Correccion de Azimut Por Curvatura: "
        +corrAzimutPorCurv);

    //Azimut Plano
    double azimutPlano =
        Math.toDegrees(Math.acos((coordNorteA-coordNorteB)/longProyectada));
    System.out.println("Azimut Plano: "+azimutPlano);


    //Azimut Geodesico
    double azimutGeodesico = (azimutPlano+corrAzimutPorCurv);
    System.out.println("Azimut Geodesico: "+azimutGeodesico);
    //71.76275555555556


    //Coordenadas Topograficas del Pto "B"
    double coordEsteBtop = (coordEsteA+distTopografica*
        (Math.sin(Math.toRadians(azimutGeodesico))));
    double coordNorteBtop = (coordNorteA-distTopografica*
        (Math.cos(Math.toRadians(azimutGeodesico))));

    System.out.println("ESTE: "+coordEsteBtop);
    System.out.println("NORTE: "+coordNorteBtop);
  }
}