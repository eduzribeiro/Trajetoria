addpath('..\')

x = media_g_pausa;

dados = load('..\data\yurei7.txt');

a = dados(:,2:4); % Acelerômetro

Db = zeros(1,2508);
Db(15:400)=1;

for II=1:max(size(a))
    
          
    
    [g0n(II,:) ready(II)] = x.g_pausa_rt(a(II,:),Db(II));
   

  
end

b = [1:max(size(a))];

plot(b,a(:,3),'s-',b,g0n(:,3),'o-',b,ready(:),'^-',b,Db(:),'s-')
legend('acc','g0n','ready','Db')


rmpath('..\')
