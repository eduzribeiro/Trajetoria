%%
a=10*rand(1,3);
b=a+0.2*norm(a)*rand(1,3);
b=b*norm(a)/norm(b);

T=inverse_problem2(a,b)

a
b
(T*a')'
