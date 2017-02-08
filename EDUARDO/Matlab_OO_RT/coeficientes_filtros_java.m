clear all
close all
clc

% P=13;
% H=fir1(4*P,1/P,'high');

HM=ones(1,20);
HM=HM/sum(HM);

% P1 = 20;
% H1 = fir1(4*P1,1/P1,'low');


%save('C:\Users\Eduardo\Desktop\Diversos\UFLA\Mestrado\Trajetoria\EDUARDO\JAVA\CoeficientesFiltros\ValoresH_so_pausa_fir.dat','H','-ascii')

save('C:\Users\Eduardo\Desktop\Diversos\UFLA\Mestrado\Trajetoria\EDUARDO\JAVA\CoeficientesFiltros\ValoresH_detectorpausa_inicio.dat','HM','-ascii')

%save('C:\Users\Eduardo\Desktop\Diversos\UFLA\Mestrado\Trajetoria\EDUARDO\JAVA\CoeficientesFiltros\ValoresH_so_ativo_fir.dat','H1','-ascii')
