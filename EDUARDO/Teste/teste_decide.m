addpath('..\')

dados = load('..\data\yurei7.txt');

a = dados(:,2:4); % Acelerômetro


x = setup_function_rt_media_l(400);

y = media_g_pausa;

z = decide(2);

Db = 0;



for II=1:max(size(a))

    g(II,:) = x.media_de_l(a(II,:));
    
    if II == 600
       
        Db = 1;
        
    end
    
    if II == 900
       
        Db = 0;
        
    end
    
    if II == 1200
       
        Db = 1;
        
    end
    
    if II == 1450
       
        Db = 0;
        
    end
        
    
    [g0n(II,:) ready(II)] = y.g_pausa_rt(a(II,:),Db);
    
     Xd(II,:) = z.decide_rt(g(II,:),g0n(II,:),ready(II));
   
       

end

b = [1:max(size(a))];

plot(b,a(:,3),'s-',b,g(:,3),'o-')


plot(b,a(:,3),'.-',b,g(:,3),'*-',b,g0n(:,3),'o-',b,ready(:),'^-',b,Xd(:,3),'s-')
legend('acc','g','g0n','ready','Xd')


rmpath('..\')
