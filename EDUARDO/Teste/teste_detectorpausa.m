addpath('..\')

clear
clc


x = detectordepausa_rt;

r0=setup_function_rt_ruido_l(1000);

dados = load('..\data\yurei7.txt');

a = dados(:,2:4); % Acelerômetro

Db = zeros(1,2508);

ruido = zeros(1,2508);

D1 = zeros(1,2508);

D2 = zeros(1,2508);

for II=1:max(size(a))
    
        
    ruido(II) = r0.ruido_de_l(a(II,:)');
    
    Db(II) = x.detector_pausa(a(II,:)',ruido(II));
    
    D1(II) = x.Dx;
    
    D2(II) = x.Dz;

  
end

b = [1:max(size(a))];

plot(b,a(:,1),'s-',b,Db(:),'s-',b,ruido(:),'s-')
legend('acc','Db','Ruido')


rmpath('..\')

