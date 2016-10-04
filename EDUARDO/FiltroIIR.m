classdef FiltroIIR < handle
   properties
        H
        G
        X
        Y

   end
   methods
        function obj = FiltroIIR(H,G)
        
            obj.X = zeros(3,size(H,2));
            obj.Y = zeros(3,size(G,2));
            obj.H = H;
            obj.G = G;
            
            
        end
        %x e coluna
        function y = filter_rt(obj,x)
            %correr X
            M=length(obj.H);
            for II=1:(M-1)
                obj.X(:,M-II+1)=obj.X(:,M-II);
            end
            obj.X(:,1)=x;

            
            
            N=length(obj.G);
            for II=1:(N-1)
                obj.Y(:,N-II+1)=obj.Y(:,N-II);
            end
            obj.Y(:,1)=[0;0;0];
            
            %proccess
            
            y = [0;0;0];
            
            y(1) = (sum(obj.X(1,:).*obj.H)-sum(obj.Y(1,:).*obj.G) )/obj.G(1);
            y(2) = (sum(obj.X(2,:).*obj.H)-sum(obj.Y(2,:).*obj.G) )/obj.G(1);
            y(3) = (sum(obj.X(3,:).*obj.H)-sum(obj.Y(3,:).*obj.G) )/obj.G(1);
            
            obj.Y(:,1)=y;
            
        end

   end
end