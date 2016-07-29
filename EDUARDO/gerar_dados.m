
L=240;


A = imread('trajetoria.bmp');

[iTotal jTotal] = size(A);

 
for II=1:jTotal
   
    [P1(II) Y(II)] = min(A(:,II));
    
    X(II)= II;
    
    T(II)=100*(II)^0.4;
    
end

% Px = polyfit(T,X,11);
% 
% Py = polyfit(T,Y,11);
% 
% X = polyval(Px,T);
% 
% Y = polyval(Py,T);

X = smooth(X,L,'loess')';

Y = smooth(Y,L,'loess')';

figure(1)
plot(T,X,'-s',T,Y,'-o')
title('Distância x Tempo')

% X = 0 : .1 : 4*pi;
% 
% Y = sin(X);
% 
% plot(X,Y,'r')
% 
% title('Gráficos - Teste 02')
% 
% xlabel('t')
% 
% ylabel('sent')

Vx = zeros(size(X));
Vy = zeros(size(Y));

for II=2:jTotal
   
    dT = T(II)-T(II-1);
    
    Vx(II) = (X(II)-X(II-1))/dT;
    Vy(II) = (Y(II)-Y(II-1))/dT;
    
end

 figure(2)
 plot(T,Vx,'-o',T,Vy,'-o')
 title('Velocidade')

ax = zeros(size(X));
ay = zeros(size(Y));

for II=2:jTotal
    
    dT = T(II)-T(II-1);
   
    ax(II) = (Vx(II)-Vx(II-1))/dT;
    ay(II) = (Vy(II)-Vy(II-1))/dT;
    
end

 figure(3)
 plot(T,ax,'-o',T,ay,'-o')
 title('Aceleração')

Dados = [T' ax' ay' 0*ax' zeros(size(ax,2),9)];

save('Dados.txt','Dados','-ascii')

figure(4)
plot(X,Y,'-o')
%xlim([400 1000])


