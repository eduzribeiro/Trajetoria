addpath('..\')

clear
clc

%Carregar dados

dados = load('..\data\slide4.txt');

%Separar colunas

t = dados(:,1); % Tempo 
t(:)=t(:)-t(1);

ar = dados(:,2:4); % Acelerômetro

wr = dados(:,5:7); % Girômetro

%Variáveis

Db = 0;

% ruido0 = zeros(size(ar(:,1)));
% a = zeros(size(ar));
% g0 = zeros(size(ar));
% w = zeros(size(ar));
% a_n = zeros(size(ar));
% a_n2 = zeros(size(ar));
% ruido_d = zeros(size(ar(:,1)));

%Intervalo de tempo dt

iTotal = size(dados,1);

dt(1) = 0;


for JJ=1:iTotal

    if (JJ>1)
        dt(JJ) = (t(JJ)-t(JJ-1)); 
    end
    
     
end

%Classes

S = setup(1500);

%Processo

for II=1:max(size(ar))
    
    %Inicialização

    [ruido0(II), a(II,:), g0(II,:), w(II,:)] = S.setup_rt(ar(II,:),wr(II,:));
	

end

b = [1:max(size(ar))];

figure(1) 
plot(b,ruido0,b,a(:,3),b,g0(:,3),b,w(:,3))
legend('ruido','a descarte','g0','w restado')



rmpath('..\')

