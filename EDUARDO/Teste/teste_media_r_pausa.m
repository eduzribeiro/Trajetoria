addpath('..\')

x = media_r_pausa;

dados = load('..\data\yurei7.txt');

a = dados(:,2:4); % Acelerômetro

Db = zeros(1,2508);
Db(15:2200)=1;

for II=1:max(size(a))
    
        
    
    [ruido(II) ready(II)] = x.r_pausa_rt(a(II,:)',Db(II));
   

  
end

b = [1:max(size(a))];

plot(b,a(:,1),'s-',b,ruido(:),'o-',b,ready(:),'^-',b,Db(:),'s-')
legend('acc','ruido0n','ready','Db')


rmpath('..\')
