
L=240;


A = imread('trajetoria12.bmp');


[iTotal jTotal] = size(A);

 
for II=1:jTotal
   
    [P1(II) Y(II)] = min(A(:,II));
    
    X(II)= II;
    
end

% Px = polyfit(T,X,11);
% 
%Py = polyfit(X,Y,22);
% 
% X = polyval(Px,T);
% 
%Y = polyval(Py,X);
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%X = smooth(X,L,'loess')';
L=20;
h=fir1(L,0.1);
Y=filter(h,1,Y);

%Y = smooth(Y,0.1,'loess'); %  Y=Y-min(Y);
%Y = fit(X,Y,'smoothingspline');
for II=1:jTotal    
    T(II)=100*(II)^0.7;
end


figure(1)
plot(T,X,'-s',T,Y,'-o')
legend('X','Y')
title('Distância x Tempo')

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

Vx = zeros(size(X));
Vy = zeros(size(Y));

for II=2:jTotal
   
    dT = T(II)-T(II-1);
    
    Vx(II) = (X(II)-X(II-1))/dT;
    Vy(II) = (Y(II)-Y(II-1))/dT;
    
end

 figure(2)
 plot(T,Vx,'-s',T,Vy,'-o')
 legend('X','Y')
 title('Velocidade')
 
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
ax = zeros(size(Vx));
ay = zeros(size(Vy));

for II=2:jTotal
    
    dT = T(II)-T(II-1);
   
    ax(II) = (Vx(II)-Vx(II-1))/dT;
    ay(II) = (Vy(II)-Vy(II-1))/dT;
    
end


 figure(3)
 plot(T,ax,'-s',T,ay,'-o');
 legend('X','Y')
 title('Aceleração')
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Dados = [T' ax' ay' 0*ax' zeros(size(ax,2),9)];

save('Dados12.txt','Dados','-ascii')

figure(4)
plot(X,Y,'-o')
%xlim([400 1000])
grid on

figure(5)
 plot(X,ax,'-s',X,ay,'-o');
 legend('X','Y')
 title('Aceleração')

save('originais.mat','ax','ay','T','X');
