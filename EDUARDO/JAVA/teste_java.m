clear all
close all
clc

dados = load('logfile.txt');

Sx = dados(:,1);
Sy = dados(:,2);
Sz = dados(:,3);

figure(1) 
plot(Sx,Sy,'-o')
xlabel('x')
ylabel('y')
title('Deslocamento (m)')
% xlim([-0.1 0.65]);
% ylim([-0.1 0.65]);

% figure(2)
% plot3(Sx,Sy,Sz,Sx(end),Sy(end),Sz(end),'o')
% grid on
% xlabel('X (m)');
% ylabel('Y (m)');
% zlabel('Z (m)');
% MAX=max([Sx Sy Sz]);
% MIN=min([Sx Sy Sz]);
% xlim([-0.1 0.65]);
% ylim([-0.1 0.65]);