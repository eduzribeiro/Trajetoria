clear all
close all
clc

xOR = load('C:\Users\Eduardo\Desktop\Diversos\UFLA\Mestrado\Trajetoria\EDUARDO\data\Resultados - Originnal - Spline - Kalman\Comparações QuedaLivre\Original\SxD.mat');
yOR = load('C:\Users\Eduardo\Desktop\Diversos\UFLA\Mestrado\Trajetoria\EDUARDO\data\Resultados - Originnal - Spline - Kalman\Comparações QuedaLivre\Original\SyD.mat');
zOR = load('C:\Users\Eduardo\Desktop\Diversos\UFLA\Mestrado\Trajetoria\EDUARDO\data\Resultados - Originnal - Spline - Kalman\Comparações QuedaLivre\Original\SzD.mat');

xK = load('C:\Users\Eduardo\Desktop\Diversos\UFLA\Mestrado\Trajetoria\EDUARDO\data\Resultados - Originnal - Spline - Kalman\Comparações QuedaLivre\Kalman\SxD.mat');
yK = load('C:\Users\Eduardo\Desktop\Diversos\UFLA\Mestrado\Trajetoria\EDUARDO\data\Resultados - Originnal - Spline - Kalman\Comparações QuedaLivre\Kalman\SyD.mat');
zK = load('C:\Users\Eduardo\Desktop\Diversos\UFLA\Mestrado\Trajetoria\EDUARDO\data\Resultados - Originnal - Spline - Kalman\Comparações QuedaLivre\Kalman\SzD.mat');

xSP = load('C:\Users\Eduardo\Desktop\Diversos\UFLA\Mestrado\Trajetoria\EDUARDO\data\Resultados - Originnal - Spline - Kalman\Comparações QuedaLivre\Spline\SxD.mat');
ySP = load('C:\Users\Eduardo\Desktop\Diversos\UFLA\Mestrado\Trajetoria\EDUARDO\data\Resultados - Originnal - Spline - Kalman\Comparações QuedaLivre\Spline\SyD.mat');
zSP = load('C:\Users\Eduardo\Desktop\Diversos\UFLA\Mestrado\Trajetoria\EDUARDO\data\Resultados - Originnal - Spline - Kalman\Comparações QuedaLivre\Spline\SzD.mat');

plot(xOR.SxD,zOR.SzD,xSP.SxD,zSP.SzD,'r');
grid on
xlim([-0.7,0.7]);
ylim([-0.7,0.7]);
xlabel('X (m)');
ylabel('Z (m)');
legend('Original','Spline')