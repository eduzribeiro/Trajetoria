
x = setup_function_rt_media_l(0);

dados = load('data\yurei7.txt');

a = dados(:,2:4); % Acelerômetro

for II=1:max(size(a))

    g(II,:) = x.media_de_l(a(II,:),1500);

end

b = [1:max(size(a))];

plot(b,a(:,3),'s-',b,g(:,3),'o-')


