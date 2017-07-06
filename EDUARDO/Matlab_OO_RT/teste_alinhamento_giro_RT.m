addpath('..\')

clear all
close all
clc

%Carregar dados

dados = load('C:\Users\Eduardo\Desktop\Diversos\UFLA\Mestrado\Trajetoria\EDUARDO\JAVA\com\eduardo\funcoes\logfile.txt');

ACC = load('C:\Users\Eduardo\Desktop\Diversos\UFLA\Mestrado\Trajetoria\EDUARDO\Legado\originais.mat');

%Separar colunas

t = dados(:,1); % Tempo 
%t(:)=t(:)-t(1);

ar = dados(:,2:4); % Acelerômetro

wr = dados(:,5:7); % Girômetro

%Variáveis
DbDecidido = 0;
Db = 0;

% ruido0 = zeros(size(ar(:,1)));
% a = zeros(size(ar));
% g0 = zeros(size(ar));
% w = zeros(size(ar));
% a_n = zeros(size(ar));
% a_n2 = zeros(size(ar));
% ruido_d = zeros(size(ar(:,1)));

%Intervalo de tempo dt

iTotal = size(dados,1);

dt(1) = 0;


for JJ=1:iTotal

    if (JJ>1)
        dt(JJ) = (t(JJ)-t(JJ-1)); 
    end
    
     
end

%Classes

S = setup(1);

MA = matriz_transf_rt;

DEG = decide_g;

DER = decide_r;

DETEC = detectordepausa_rt(1);

DETEC2 = detectordepausa_inicio(1);

INT = Integrador(0);

DEC = decide_detector;


%Processo

for II=1:max(size(ar))
    
    %Inicialização

    [ruido0(II), a(II,:), g0(II,:), w(II,:)] = S.setup_rt(ar(II,:),wr(II,:));
	
    %Detector novo
    
    Db3(II) = DETEC2.detector_inicio(a(II,:),ruido0(II));
    
    %Matriz 
    
    M = MA.matriz_transf(w(II,:),dt(II),DbDecidido);
    
    % Atualizar matriz
     
    a_n(II,:) = (M*a(II,:)')';
    
    %Calcular e decidir gravidade
    
    a_n2(II,:) = DEG.decide_g_rt(a_n(II,:),g0(II,:),Db);
    
       
    %Calcular e decidir ruido
    
    ruido_d(II) = DER.decide_r_rt(a_n(II,:),ruido0(II),Db);
    
    %Detector de pausa
    
    [Db,Dma(II)] = DETEC.detector_pausa(a_n2(II,:)',ruido_d(II));
    
    DSP(II) = DETEC.Dsp;
    
    DSA(II) = DETEC.Dsa;
    
    Db2(II) = Db;
    
    %Agrupar detectores de pausa
    
    DbDecidido = DEC.decidido(Db,Db3(II));
    
    DbDecidido2(II) = DbDecidido;
       
    %Integral
    
    [Sx(II,:) Sy(II,:) Sz(II,:)] = INT.integra_rt(dt(II),a_n2(II,:),DbDecidido);
    
    VX(II) = INT.Vx;
    VY(II) = INT.Vy;
    VZ(II) = INT.Vz;
		

end

b = [1:max(size(a))];

figure(1) 
plot(Sx,Sy)
xlabel('X (m)')
ylabel('Y (m)')
title('Deslocamento (m)')
% MAX=max(max([Sx Sy]));
% MIN=min(min([Sx Sy]));
% xlim([MIN MAX]);
% ylim([MIN MAX]);
grid on
%xlim([-0.7 0.7]);
%ylim([-0.7 0.7]);


figure(2)
plot3(Sx,Sy,Sz,Sx(end),Sy(end),Sz(end),'o')
grid on
xlabel('X (m)');
ylabel('Y (m)');
zlabel('Z (m)');
title('Deslocamento (m)')
MAX=max(max([Sx Sy Sz]));
MIN=min(min([Sx Sy Sz]));
xlim([MIN MAX]);
ylim([MIN MAX]);
zlim([MIN MAX]);

figure(3)
plot(t,a_n2(:,1),'-o',t,a_n2(:,2),'-o',t,a_n2(:,3),'-<',ACC.T,ACC.ay,'->');%,b,DbDecidido2*3,'k');%,'-o',b,Db2,b,Db3*2,b,DbDecidido2*3)%,b,DSA,'-o',b,DSP*1.5,'-s',b,ruido_d*3)
title('Comportamento aceleração (m/s^2)')
legend('X','Y','Z','Db','DbInicio','DbDecidido')%,'Dsa','Dsp','ruido')
xlabel('Amostra');
ylabel('Aceleração');

figure(4)
plot(b,VX,b,VY,b,VZ)
title('Velocidade (m/s)')
grid on
xlabel('Amostra');
ylabel('Velocidade');
legend('Vx','Vy','Vz')

% SxD = Sx(1082:1175);
% SyD = Sy(1082:1175);
% SzD = Sz(1082:1175);
% 
% figure(5)
% plot3(SxD,SyD,SzD);
% grid on
% xlabel('X (m)');
% ylabel('Y (m)');
% zlabel('Z (m)');
% title('Deslocamento (m)')
% MAX=max(max([SxD SyD SzD]));
% MIN=min(min([SxD SyD SzD]));
% xlim([MIN MAX]);
% ylim([MIN MAX]);
% zlim([MIN MAX]);



rmpath('..\')

