addpath('..\')

clear
clc

%Carregar dados

dados = load('..\data\slide4.txt');

%Separar colunas

t = dados(:,1); % Tempo 
t(:)=t(:)-t(1);

ar = dados(:,2:4); % Acelerômetro

wr = dados(:,5:7); % Girômetro

%Variáveis

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

S = setup(1500);

MA = matriz_transf_rt;

DEG = decide_g;

DER = decide_r;

DETEC = detectordepausa_rt;

INT = Integrador(0);


%Processo

for II=1:max(size(ar))
    
    %Inicialização

    [ruido0(II), a(II,:), g0(II,:), w(II,:)] = S.setup_rt(ar(II,:),wr(II,:));
	
    %Matriz 
    
    M = MA.matriz_transf(w(II,:),dt(II),Db);
    
    % Atualizar matriz
     
    a_n(II,:) = (M*a(II,:)')';
    
    %Calcular e decidir gravidade
    
    a_n2(II,:) = DEG.decide_g_rt(a_n(II,:),g0(II,:),Db);
    
       
    %Calcular e decidir ruido
    
    ruido_d(II) = DER.decide_r_rt(a_n(II,:),ruido0(II),Db);
    
    %Detector de pausa
    
    Db = DETEC.detector_pausa(a_n2(II,:)',ruido_d(II));
    
    DSP(II) = DETEC.Dsp;
    
    DSA(II) = DETEC.Dsa;
    
    Db2(II) = Db;
    
       
    %Integral
    
    [Sx(II,:) Sy(II,:) Sz(II,:)] = INT.integra_rt(dt(II),a_n2(II,:),Db);
    
    VX(II) = INT.Vx;
    VY(II) = INT.Vy;
    VZ(II) = INT.Vz;
		

end

b = [1:max(size(a))];

figure(1) 
plot(Sx,Sy,'-o')
xlabel('x')
ylabel('y')
title('Deslocamento (m)')
xlim([-0.1 0.65]);
ylim([-0.1 0.65]);

figure(2)
plot3(Sx,Sy,Sz,Sx(end),Sy(end),Sz(end),'o')
grid on
xlabel('X (m)');
ylabel('Y (m)');
zlabel('Z (m)');
MAX=max([Sx Sy Sz]);
MIN=min([Sx Sy Sz]);
xlim([-0.1 0.65]);
ylim([-0.1 0.65]);
%zlim([MIN MAX]);

figure(3)
plot(b,a_n2(:,1),b,a_n2(:,2),b,a_n2(:,3),b,Db2*2,b,DSA,'-o',b,DSP*1.5,'-s',b,ruido_d*2.5)
legend('x2','y2','z2','Db','Dsa','Dsp','ruido')

figure(4)
plot(b,VX,b,VY,b,VZ)
grid on
legend('Vx','Vy','Vz')

rmpath('..\')

