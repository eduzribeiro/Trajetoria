L=1000;
N=[0:L-1];

a_n=zeros(L,3);
a_n(:,1)=sin(32*N*2*pi/(L))+1;

%a_n2=filtrado_hp(a_n);
a_n2=filtrado_iir_hp(a_n);

figure(2)
plot(N,a_n(:,1),'-o',N,a_n2(:,1),'-o');