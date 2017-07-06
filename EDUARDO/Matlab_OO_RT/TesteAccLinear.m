clear all
close all
clc

dados = load('C:\Users\Eduardo\Desktop\Diversos\UFLA\Mestrado\Trajetoria\EDUARDO\data\Testes paper\V3 (acc linear)\pausa-inicial-final.txt');

iTotal = size(dados,1);

v = [1:iTotal];

t = dados(:,1); % Tempo 
t(:)=t(:)-t(1);

al = dados(:,8:10); % Acelerômetro

dt(1) = 0;


for JJ=1:iTotal

    if (JJ>1)
        dt(JJ) = (t(JJ)-t(JJ-1)); 
    end
    
     
end

S = setup(1500);

INT = Integrador(0);

for II=1:max(size(al))
    
    [ruido0(II), a(II,:), g0(II,:), al2(II,:)] = S.setup_rt(0,al(II,:));
     
    %Integral
    
    [Sx(II,:) Sy(II,:) Sz(II,:)] = INT.integra_rt(dt(II),al2(II,:),0);
    
    VX(II) = INT.Vx;
    VY(II) = INT.Vy;
    VZ(II) = INT.Vz;
		

end

figure(1) 
plot(Sx,Sy,'-o')
xlabel('X (m)')
ylabel('Y (m)')
title('Deslocamento (m)')
MAX=max(max([Sx Sy]));
MIN=min(min([Sx Sy]));
xlim([MIN MAX]);
ylim([MIN MAX]);

figure(2)
plot3(Sx,Sy,Sz,Sx(end),Sy(end),Sz(end),'o')
grid on
title('Deslocamento (m)')
xlabel('X (m)');
ylabel('Y (m)');
zlabel('Z (m)');

figure(3)
plot(v,Sx,'-s',v,Sy,'-o',v,Sz,'-<')
legend('Sx','Sy','Sz');
xlabel('Tempo')
ylabel('Deslocamento (m)')
