addpath('..\')

x=setup_function_rt_ruido_l(1500);

dados = load('..\data\yurei7.txt');

a = dados(:,2:4); % Aceler�metro

ruido = zeros(size(a(:,1)));

for II=1:max(size(a))

    ruido(II) = x.ruido_de_l(a(II,:)');

end

b = [1:max(size(a))];

plot(b,a(:,1),'s-',b,ruido,'o-')

std(a(1:1500,1))

std(a(1:1500,2))

std(a(1:1500,3))


ruido(end)

rmpath('..\')