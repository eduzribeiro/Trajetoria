L=1000;
N=[0:L-1];

ruido=0.05;
sigma=L/6;
mu=L/2;
W=exp(-0.5*((N-mu)/sigma).^16);

a_n=zeros(L,3);
a_n(:,1)=W.*sin(64*N*2*pi/(L))+ruido*0.9*sqrt(2)*2*(rand(size(N))-0.5);
a_n(:,2)=a_n(:,1);
a_n(:,3)=a_n(:,1);
%a_n2=filtrado_hp(a_n)
[D Da Db]=detector_pausa(a_n,ruido);


ruidorms = nivel_ruido(a_n,200);

figure(2)
plot(N,a_n(:,1),'-o',N,Db,'-<');

legend('Sinal','Db')