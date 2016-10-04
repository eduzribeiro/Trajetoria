close all

h = dados_novos('data\yurei1.txt','Dados2.txt',1,1);
dados = load('Dados2.txt');

t = dados(:,1); % Tempo 
a = dados(:,2:4); % Acelerômetro
w = dados(:,5:7); % Girômetro

L = 200;
t1 = t(1:L,1); % Tempo 
a1=a(1:L,1);a1=a1-mean(a1);
a2=a(1:L,2);a2=a2-mean(a2);
a3=a(1:L,3);a3=a3-mean(a3);

tt = linspace(t1(1),t1(end),2*length(t1));
as1 = spline(t1,a1,tt);
as2 = spline(t1,a2,tt);
as3 = spline(t1,a3,tt);

f = linspace(0,1,length(as1));

f1 = abs(fft(as1));
f2 = abs(fft(as2));
f3 = abs(fft(as3));

figure(1)
plot(f,f1,'-s',f,f2,'-o',f,f3,'->')

figure(2)
plot(tt,as1,'-s',tt,as2,'-o',tt,as3,'->')

figure(3)
plot(t1,a1,'-s',t1,a2,'-o',t1,a3,'->')

