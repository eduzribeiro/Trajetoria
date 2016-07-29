function M=inverse_problem(gc,gn)

    Nx=gc;
    Ny=[rand(1) rand(1) 0];Ny(3)=-Ny(1)*Nx(1)/Nx(3)-Ny(2)*Nx(2)/Nx(3);
    N=[Nx Ny];%[1 0 0 0 1 0];

    %procurar o menor valor do vetor solução.
    R=5*eye(6);

    %Peso de cada elemento
    P=eye(6);
    %P(4,4)=3;    P(5,5)=3;    P(6,6)=3;
    

    VALUE0=0.14473;

    NORM0=norm(funcF(N,gc,gn))

    II=0;
    while ( (NORM0>VALUE0) && II<20 )

        F=funcF(N,gc,gn);
        Jf=funcJ(N,gc,gn);
        MAT=Jf'*P*Jf;
        if (det(MAT)==0)
            N=N+0.05*rand(1,6);

            F=funcF(N,gc,gn);
            Jf=funcJ(N,gc,gn);
            MAT=Jf'*P*Jf;
        end

        N=N-inv(MAT+R'*R)*Jf'*P*F;

        %Nx=[N(1) N(2) N(3)]
        %Ny=[N(3) N(4) N(5)]
        %F=funcF(N,gc,gn)'
        NORM0=norm(funcF(N,gc,gn));
        II=II+1;
    end

    NORM0=norm(funcF(N,gc,gn));
    if (  NORM0>VALUE0 )
        M=eye(3);
    else
        M=[N(1) N(2) N(3); N(4) N(5) N(6);cross([N(1) N(2) N(3)], [N(4) N(5) N(6)])];
    end
end



function F=funcF(N,gc,gn)
    F=zeros(6,1);
    
    Nx=[N(1) N(2) N(3)];
    Ny=[N(3) N(4) N(5)];

    F(1)=dot(Nx,gc)           - gn(1);
    F(2)=dot(Ny,gc)           - gn(2);
    F(3)=dot(cross(Nx,Ny),gc) - gn(3);
    F(4)=dot(Nx,Ny);
    F(5)=dot(Nx,Nx)-1;
    F(6)=dot(Ny,Ny)-1;
end

function Jf=funcJ(N,gc,gn)
    Jf=zeros(6,6);

    Jf(1,:)=[gc(1) gc(2) gc(3) 0     0     0];
    Jf(2,:)=[0     0     0     gc(1) gc(2) gc(3)];

    Jf(3,1)=+gc(3)*N(5)-gc(2)*N(6);
    Jf(3,2)=+gc(1)*N(6)-gc(3)*N(4);
    Jf(3,3)=+gc(2)*N(4)-gc(1)*N(5);
    Jf(3,4)=+gc(2)*N(3)-gc(3)*N(2);
    Jf(3,5)=+gc(3)*N(1)-gc(1)*N(3);
    Jf(3,6)=+gc(1)*N(2)-gc(2)*N(1);

    Jf(4,:)=[N(4)   N(5)   N(6)   N(1)   N(2)   N(3)  ];
    Jf(5,:)=[2*N(1) 2*N(2) 2*N(3) 0      0      0     ];
    Jf(6,:)=[0      0      0      2*N(4) 2*N(5) 2*N(6)];

end
