format long

clear all
close all
clc

bias = 1300;

%% Carregar dados

h = dados_novos('data\slide4.txt','Dados2.txt',1,1);

dados = load('Dados2.txt');
%dados=dados(1:310,:);
%% Separar colunas

t = dados(:,1); % Tempo 
t(:)=t(:)-t(1);

a = dados(:,2:4); % Acelerômetro

w = dados(:,5:7); % Girômetro

%  disp('Giro')
% 
 [x y z]=filtrado_dados_resta(w,1,bias)
% 
 w=filtrado_resta(w,x,y,z);

% L =9;
% w(:,1) = smooth(w(:,1),L);
% w(:,2) = smooth(w(:,2),L);
% w(:,3) = smooth(w(:,3),L);

% w=filtrado_notch(w);
%w=filtrado_hp(w);
%w=filtrado_lp(w);

%  w(:,1) = w(:,1)-2.7511e-04; %Média de todos os valores sensor estático
%  w(:,2) = w(:,2)-6.3564e-03;
%  w(:,3) = w(:,3)-1.7355e-03;
%  
%w=filtrado_hp(w);

g = -dados(:,8:10); % -(Sensor de gravidade)


%----------------------------------------------------------------------

%% Utilizar dados giro (w)

iTotal = size(dados,1);

 v = [1:iTotal];

dt(1) = 0;

R=cell(1,iTotal);

M=eye(3);
g_n=zeros(size(g));
a_n=zeros(size(a));
%g_n2=zeros(size(g));
R{1}=zeros(3,3);

ew=zeros(iTotal,3);

%a = filtrado_ap(a);


for II=1:iTotal

    mod_w = sqrt(w(II,1)^2 + w(II,2)^2 + w(II,3)^2); %módulo de w
    if(mod_w>0)
        ew(II,1) = w(II,1)/(mod_w); %ewx
        ew(II,2) = w(II,2)/(mod_w); %ewy
        ew(II,3) = w(II,3)/(mod_w); %ewz
    end
    %Obter Delta t
    if (II>1)
        dt(II) = (t(II)-t(II-1)); 
    end
    
    %Obter theta
    
    theta(II) = mod_w*dt(II); 

    
    %% Matriz de rotação
    % Vi+1 = RiVi

    R{II}(1,1) = cos(theta(II)) + (ew(II,1)^2)*(1-cos(theta(II)));
    R{II}(1,2) = ew(II,1)*ew(II,2)*(1-cos(theta(II))) - ew(II,3)*sin(theta(II));
    R{II}(1,3) = ew(II,1)*ew(II,3)*(1-cos(theta(II))) + ew(II,2)*sin(theta(II));

    R{II}(2,1) = ew(II,2)*ew(II,1)*(1-cos(theta(II))) + ew(II,3)*sin(theta(II));
    R{II}(2,2) = cos(theta(II)) + (ew(II,2)^2)*(1-cos(theta(II)));
    R{II}(2,3) = ew(II,2)*ew(II,3)*(1-cos(theta(II))) - ew(II,1)*sin(theta(II));

    R{II}(3,1) = ew(II,3)*ew(II,1)*(1-cos(theta(II))) - ew(II,2)*sin(theta(II)) ;
    R{II}(3,2) = ew(II,3)*ew(II,2)*(1-cos(theta(II))) + ew(II,1)*sin(theta(II)) ;
    R{II}(3,3) = cos(theta(II)) + (ew(II,3)^2)*(1-cos(theta(II)));
    
    %% Atualizar matriz

    M = M*(R{II});
    
   % Error = mean2(abs(M*M'- eye(3)))
   
     g_n(II,:) = (M*g(II,:)')';
     a_n(II,:) = (M*a(II,:)')';
     
end


 %% Gráficos dados gravidade
 
figure(1)
plot(v,g(:,1),'-s',v,g(:,2),'-p',v,g(:,3),'-o');
title('Gravidade original')
legend('X','Y','Z')
xlabel('Amostras')
ylabel('Aceleração(m/s²)')
ylim([-10,3]);

figure(2)
plot(v,g_n(:,1),'-s',v,g_n(:,2),'-p',v,g_n(:,3),'-o');
title('Gravidade alinhada')
legend('X','Y','Z')
xlabel('Amostras')
ylabel('Aceleração(m/s²)')
ylim([-10,3]);


G=sqrt(g_n(:,1).^2+g_n(:,2).^2+g_n(:,3).^2);
figure(3)
plot(v,G)
title('Módulo da gravidade')
xlabel('Amostras')
ylabel('Aceleração(m/s²)')
ylim([-3,10]);




%% Gráficos dados aceleração

figure(4)
plot(v,a(:,1),'-s',v,a(:,2),'-p',v,a(:,3),'-o');
title('Aceleração original')
legend('X','Y','Z')
xlabel('Amostras')
ylabel('Aceleração(m/s²)')


figure(5)
plot(v,a_n(:,1),'-s',v,a_n(:,2),'-p',v,a_n(:,3),'-o');
title('Aceleração alinhada')
legend('X','Y','Z')
xlabel('Amostras')
ylabel('Aceleração(m/s²)')

%% Filtros

%a_n2=a_n;

% Valor de resta no inicio é um, e no final é outro

%disp('Acc')

[x y z]=filtrado_dados_resta(a_n,1,bias);

a_n2=filtrado_resta(a_n,x,y,z);


%a_n2=filtrado_hp(a_n2);


%a_n2=filtrado_lp(a_n);

%a_n2=filtrado_notch(a_n2);

% L=8;
% 
% a_n2(:,1) = smooth(a_n2(:,1),L);
% a_n2(:,2) = smooth(a_n2(:,2),L);
% a_n2(:,3) = smooth(a_n2(:,3),L);

% A=sqrt(a_n2(:,1).^2+a_n2(:,2).^2+a_n2(:,3).^2);
% figure(6)
% plot(t,A)
% title('Módulo da aceleração alinhada filtrado resta')
% %ylim([-3,12]);



figure(7)
plot(v,a_n2(:,1),'-s',v,a_n2(:,2),'-p',v,a_n2(:,3),'-o');
title('Aceleração alinhada e filtrada')
xlabel('Amostras')
ylabel('Aceleração(m/s²)')
legend('X','Y','Z')

%% Integração

[Sx_n,Sy_n,Sz_n,Vx_n,Vy_n,Vz_n]=integra_acel(dt,bias,a_n2);

% 
figure(8)
plot3(Sx_n,Sy_n,Sz_n,Sx_n(end),Sy_n(end),Sz_n(end),'o')
grid on
xlabel('X (m)');
ylabel('Y (m)');
zlabel('Z (m)');
MAX=max([Sx_n,Sy_n,Sz_n]);
MIN=min([Sx_n,Sy_n,Sz_n]);
xlim([MIN,MAX]);
ylim([MIN,MAX]);
zlim([MIN,MAX]);


figure(9)
%plot(v,Vx_n,'-s',v,Vy_n,'-o',v,Vz_n,'-<',v,a_n2(:,1)/20,'-s',v,a_n2(:,2)/20,'-p',v,a_n2(:,3)/20,'-o')
%legend('Vx_n','Vy_n','Vz_n','ax/20','ay/20','az/20');
plot(v,Vx_n,'-s',v,Vy_n,'-o',v,Vz_n,'-<')
legend('Vx','Vy','Vz');
xlabel('Amostras')
ylabel('Velocidade(m/s)')
%ylim([-1,0.5])

figure(10)
plot(v,Sx_n,'-s',v,Sy_n,'-o',v,Sz_n,'-<')
legend('Sx','Sy','Sz');
xlabel('Tempo')
ylabel('Deslocamento (m)')

figure(11) 
plot(Sx_n,Sy_n)
xlabel('x')
ylabel('y')
title('Deslocamento (m)')

figure(12)
plot(v,w(:,1),'-s',v,w(:,2),'-p',v,w(:,3),'-o');
title('Giro')
legend('X','Y','Z')
