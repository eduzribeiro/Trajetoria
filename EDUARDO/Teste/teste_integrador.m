addpath('..\')

x = Integrador(0);

dados = load('Dados.txt');

t = dados(:,1); % Tempo 
t(:)=t(:)-t(1);

a = dados(:,2:4); % Acelerômetro

iTotal = size(dados,1);

dt(1) = 0;

for JJ=1:iTotal

    if (JJ>1)
        dt(JJ) = (t(JJ)-t(JJ-1)); 
    end
    
     
end


for II=1:max(size(a))

    [Sx(II,:) Sy(II,:) Sz(II,:)] = x.integra_rt(dt(II),a(II,:),0);

end

plot(Sx,Sy)

rmpath('..\')