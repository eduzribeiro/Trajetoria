clear all
close all
clc


dados = load('data\pausa-ando-pausa.txt');

%dados = dados(1:106,:);

iTotal = size(dados,1);

v = [1:iTotal];

ax = dados(:,2); % Acelerômetro

ay = dados(:,3);

az = dados(:,4);

%% X

fx = abs(fft(ax));

fhzx = mean(fx(2:end))

Vx = var(fx(2:end))

figure(1)
plot(v,fx)

%% Y

fy = abs(fft(ay));

fhzy = mean(fy(2:end))

Vy = var(fy(2:end))

figure(2)
plot(v,fy)

%% Z

fz = abs(fft(az));

fhzz = mean(fz(2:end))

Vz = var(fz(2:end))

figure(3)
plot(v,fz)
