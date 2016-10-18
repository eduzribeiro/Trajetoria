addpath('..\')

x = matriz_transf_rt;

dados = load('..\data\pausa-quedalivre-pausa.txt');

t = dados(:,1); % Tempo 
t(:)=t(:)-t(1);

a = dados(:,2:4); % Acelerômetro

w = dados(:,5:7); % Giro

iTotal = size(dados,1);

v = [1:iTotal];

dt(1) = 0;

a_n=zeros(size(a));

for JJ=1:iTotal

    if (JJ>1)
        dt(JJ) = (t(JJ)-t(JJ-1)); 
    end
    
     
end


for II=1:max(size(w))

    M = x.matriz_transf(w(II,:),dt(II),0);
    
     % Atualizar matriz

      
    a_n(II,:) = (M*a(II,:)')';
   

end

figure(1)
plot(v,a(:,1),'-s',v,a(:,2),'-p',v,a(:,3),'-o');
title('Aceleração original')
legend('X','Y','Z')
xlabel('Amostras')
ylabel('Aceleração(m/s²)')


figure(2)
plot(v,a_n(:,1),'-s',v,a_n(:,2),'-p',v,a_n(:,3),'-o');
title('Aceleração alinhada')
legend('X','Y','Z')
xlabel('Amostras')
ylabel('Aceleração(m/s²)')


rmpath('..\')