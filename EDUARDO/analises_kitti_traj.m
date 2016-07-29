clear all
close all
clc

%% Carregar dados

%dados = load('C:\Users\Eduardo\Desktop\data\tudo.txt');
NIMAGES=106;
DIRECTORY='C:\Users\Eduardo\Desktop\Diversos\UFLA\Iniciação\backup\Testes trajetoria\2011_09_28_drive_0001_sync - curva\oxts\data';
dados=zeros(NIMAGES,30);

for II=1:NIMAGES
    FILE=fullfile(DIRECTORY,sprintf('%010d.txt',II-1));
    dados(II,:) = load(FILE);
end
%% Separar colunas

for II =1:NIMAGES

dt(II,1) = 0.1; % Tempo 

end

a = dados(:,12:14); % Acelerômetro

w = dados(:,18:20); % Girômetro

%w(:,1) = w(:,1)-2.7511e-04; %10 segundos parado: Média dos 10 primeiros valores
%w(:,2) = w(:,2)-6.3564e-03;
%w(:,3) = w(:,3)-1.7355e-03;

%g = -dados(:,8:10); % -(Sensor de gravidade)


%----------------------------------------------------------------------

%% Utilizar dados giro (w)

iTotal = size(dados,1);

%dt(1) = 0;

R=cell(1,iTotal);

M=eye(3);
%g_n=zeros(size(g));
a_n=zeros(size(a));
%g_n2=zeros(size(g));
R{1}=zeros(3,3);

for II=1:iTotal

    mod_w = sqrt(w(II,1)^2 + w(II,2)^2 + w(II,3)^2); %módulo de w
    
    ew(II,1) = w(II,1)/(mod_w); %ewx
    ew(II,2) = w(II,2)/(mod_w); %ewy
    ew(II,3) = w(II,3)/(mod_w); %ewz
    
    %Obter Delta t
    %if (II>1)
     %   dt(II) = (t(II)-t(II-1)); 
    %end
    
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

    M = M*(R{II});%inv(R);
   
     %g_n(II,:) = (M*g(II,:)')';
     a_n(II,:) = (M*a(II,:)')';

end

 v = [1:iTotal];

 %% Gráficos dados gravidade
 
% figure(1)
% plot(t,g(:,1),'-s',t,g(:,2),'-p',t,g(:,3),'-o');
% title('Gravidade original')
% legend('X','Y','Z')
% ylim([-10,3]);
% 
% figure(2)
% plot(t,g_n(:,1),'-s',t,g_n(:,2),'-p',t,g_n(:,3),'-o');
% title('Gravidade alinhada')
% legend('X','Y','Z')
% ylim([-10,3]);
% 
% 
% G=sqrt(g_n(:,1).^2+g_n(:,2).^2+g_n(:,3).^2);
% figure(3)
% plot(t,G)
% title('Módulo da gravidade')
% ylim([-3,10]);
% 



%% Gráficos dados aceleração

figure(1)
plot(v,a(:,1),'-s',v,a(:,2),'-p',v,a(:,3),'-o');
title('Aceleração original')
legend('X','Y','Z')


figure(2)
plot(v,a_n(:,1),'-s',v,a_n(:,2),'-p',v,a_n(:,3),'-o');
title('Aceleração alinhada')
legend('X','Y','Z')

gx = mean(a_n(1:5,1));
gy = mean(a_n(1:5,2));
gz = mean(a_n(1:5,3));

%% Filtros

%a_n2=filtrado_resta_kitti(a_n);

a_n2(:,1)=a_n(:,1)-gx;
a_n2(:,2)=a_n(:,2)-gy;
a_n2(:,3)=a_n(:,3)-gz;

%a_n2=filtrado_lp(a_n2);

%a_n2=filtrado_notch(a_n2);

A=sqrt(a_n2(:,1).^2+a_n2(:,2).^2+a_n2(:,3).^2);
figure(3)
plot(v,A)
title('Módulo da aceleração alinhada filtrado resta')
%ylim([-3,12]);


figure(4)
plot(v,a_n2(:,1),'-s',v,a_n2(:,2),'-p',v,a_n2(:,3),'-o');
title('Aceleração alinhada e filtrada resta')
legend('X','Y','Z')

%% Integração

[Sx_n,Sy_n,Sz_n,Vx_n,Vy_n,Vz_n]=integra_acel(dt, a_n2);

% 
figure(5)
plot(Sx_n,Sy_n ,Sx_n(end),Sy_n(end),'o')
MINX=min(Sx_n); MINY=min(Sy_n); MINZ=min(Sz_n);
MIN=min([MINX,MINY,MINZ]);
MAXX=max(Sx_n); MAXY=max(Sy_n); MAXZ=max(Sz_n);
MAX=max([MAXX,MAXY,MAXZ]);
xlim([MIN,MAX])
ylim([MIN,MAX])
zlim([MIN,MAX])
grid on
xlabel('X');
ylabel('Y');
zlabel('Z');

% figure(6)
% plot(t,Vx_n,'-s',t,Vy_n,'-o',t,Vz_n,'-<',t,a_n2(:,1),'-s',t,a_n2(:,2),'-p',t,a_n2(:,3),'-o')
% legend('X','Y','Z','ax','ay','az');
% xlim([3,6])
% ylim([-1,2])
% 
% figure(7)
% plot(t,Sx_n,'-s',t,Sy_n,'-o',t,Sz_n,'-<',t,A,'-*')
% legend('X','Y','Z','M');