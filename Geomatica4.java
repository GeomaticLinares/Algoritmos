import java.util.Scanner;
public class Geomatica4 {
  public static void main(String[] args) {
    Scanner entrada = new Scanner(System.in);

    //UTM a Geodesicas

    //Inicializando valores
    double semiEjeMayor = 6378137;
    double primExcentricidad = 0.00669438;
    double segExcentricidad = 0.006739497;

    System.out.println("Bienvenido: de UTM a Geodesicas");

    System.out.println("Ingrese Coordendas ESTE");
    double coordEste = entrada.nextDouble();
    System.out.println("Ingrese Coordenada NORTE");
    double coordNorte = entrada.nextDouble();
    System.out.println("Ingrese Zona UTM");
    double zonaUtm = entrada.nextDouble();

    double e1=(1-Math.sqrt(1-primExcentricidad))/
            (1+Math.sqrt(1-primExcentricidad));
    double m1= (coordNorte-10000000)/0.9996;
    double u=m1/(semiEjeMayor*(1-(primExcentricidad/4)-
            (3*Math.pow(primExcentricidad,2)/64)-
            (5*Math.pow(primExcentricidad,3)/256)));

    double latitudPrima=u+((3*e1/2)-(27*Math.pow(e1,3)/32))*
            Math.sin(2*u)+ ((21*Math.pow(e1,2)/16)-
            (55*Math.pow(e1,4)/32))*Math.sin(4*u)+ (151*(Math.pow(e1,3))/96)*
            Math.sin(6*u)+(1097*(Math.pow(e1,4))/512)*Math.sin(8*u);
    double v1=semiEjeMayor/
            (Math.sqrt(1-primExcentricidad*
                Math.pow(Math.sin(latitudPrima*Math.PI/180),2)));
    double d1=(coordEste-500000)/(v1*0.9996);
    double r1=semiEjeMayor*(1-primExcentricidad)/
            Math.sqrt(Math.pow(1-(primExcentricidad*
                Math.pow(Math.sin(latitudPrima),2)),3));
    double t1=Math.pow(Math.tan(latitudPrima),2);
    double c1=segExcentricidad*Math.pow(Math.cos(latitudPrima),2);
    double p=Math.pow(d1,2)/2;
    double q=-(5+3*t1+10*c1-4*Math.pow(c1,2)-9*
        segExcentricidad)*Math.pow(d1,4)/24;
    double s=(61+90*t1+298*c1+45*Math.pow(t1,2)-252*
        segExcentricidad-3*Math.pow(c1,2))* Math.pow(d1,6)/720;
    double deltaLatitudPrima=-(v1*Math.tan(latitudPrima)/r1)*(p+q+s);
    double latitud1=latitudPrima+deltaLatitudPrima;

    //CALCULO DE LA LATITUD
    System.out.println("********   Mostrando Latitud   *********");
    double gradLatitud=(Math.toDegrees(latitud1)-
        (Math.toDegrees(latitud1)%1));
    double minLatitud;
      if(gradLatitud<0)
        {minLatitud=(Math.toDegrees(latitud1)%1)*-60;}
      else
        {minLatitud=(Math.toDegrees(latitud1)%1)*60;}

    //Convirtiendo a Segundos
    double segundos2=(minLatitud%1)*60;

    System.out.println("Grados: "+(int)gradLatitud +"°"+"  "
        +"Minutos: "+(int)minLatitud + "´"+"  "
        + "Segundos: "+String.format("%.4f",segundos2));

    //CALCULO DE PARAMETROS PARA LONGITUD
    double deltaCero=zonaUtm*6-183;
    double jj=d1-(1+2*t1+c1)*Math.pow(d1,3)/6;
    double xx=(5-2*c1+28*t1-3*Math.pow(c1,2)+8*
        segExcentricidad+24*Math.pow(t1,2))* Math.pow(d1,5)/120;

    double delta=(jj+xx)/Math.cos(latitudPrima);
    double longitud1=deltaCero+(delta*180/Math.PI);
    System.out.println("*Mostrando Longitud*");
    double gradLong=(longitud1-(longitud1%1));

    //Convirtiendo a Minutos
    double  minLong;
    //cambiando de signo
    if(gradLong<0)
    {minLong=(longitud1%1)*-60;}
    else
    {minLong=(longitud1%1)*60;}

    double segLong=( minLong%1)*60;

    System.out.println("Grados: "+(int)gradLong +"°"+"  "
        +"Minutos: "+(int) minLong + "´"+"  "
        + "Segundos: "+String.format("%.4f",segLong));
  }
}
