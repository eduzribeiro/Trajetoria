addpath('..\')

x = setup_function_rt_media_l(1500);

dados = load('..\data\yurei7.txt');

a = dados(:,2:4); % Acelerômetro

% for U=1:2508
%    
%     a(U,1) = U/2;
%     a(U,2) = U-1;
%     a(U,3) = U;
%     
%     
% end

for II=1:max(size(a))

    g(II,:) = x.media_de_l(a(II,:));

end

b = [1:max(size(a))];

plot(b,a(:,3),'s-',b,g(:,3),'o-')

rmpath('..\')


