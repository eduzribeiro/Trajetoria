clear all
close all
clc

[H,G] = fir1(16,1/100,'low');

F = FiltroIIR(H,G);

dados = load('data\yurei7.txt');

b = dados(:,2:4)'; % Acelerômetro

for II=1:max(size(b))

    a(:,II) = b(:,II) - F.filter_rt(b(:,II));
    
    
end

c = [1:max(size(b))];

figure(1)

plot(c,a(1,:),'r',c,a(2,:),'b',c,a(3,:),'g')
legend('X','Y','Z')

