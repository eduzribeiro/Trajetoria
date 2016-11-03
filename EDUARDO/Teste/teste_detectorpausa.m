addpath('..\')

clear
clc


x = detectordepausa_rt;

r0=setup_function_rt_ruido_l(1500);

dados = load('..\data\slide4.txt');

a = dados(:,2:4); % Acelerômetro

Db = zeros(1,2400);

ruido = zeros(1,2400);

D1 = zeros(1,2400);

D2 = zeros(1,2400);

for II=1:max(size(a))
    
        
    ruido(II) = r0.ruido_de_l(a(II,:)');
    
    Db(II) = x.detector_pausa(a(II,:)',ruido(II));
    
    D1(II) = x.Dx;
    
    D2(II) = x.Dz;

  
end

b = [1:max(size(a))];

plot(b,a(:,1),b,a(:,2),b,a(:,3),'s-',b,Db(:),'s-',b,ruido(:),'s-')
legend('accx','accy','accz','Db','Ruido')


rmpath('..\')

