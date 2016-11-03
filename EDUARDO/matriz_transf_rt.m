% Classe que calcula a matriz de transformação R
classdef matriz_transf_rt < handle
   properties
        R
        ew
        theta
        mod_w
        M
   end
   methods
       %Construtor inializador do objeto
       % inival: inicia todas as variaveis
        function obj = matriz_transf_rt
        
            obj.R = [0 0 0; 0 0 0; 0 0 0];
            obj.ew = [0 0 0];
            obj.theta =0;
            obj.mod_w=0;
            obj.M = eye(3);
            
        end
        
        %w: input signal  [1x3] ou [3x1]
        %dt: input time interval [1x1]
        %return :::
        %R: Matriz de transformação [3x3]
        
                
        function M=matriz_transf(obj,w,dt,DB)
            
            
           obj.R=obj.matriz_r(w,dt);
           
           
           if(DB==1)
               obj.M=eye(3);
           else
               obj.M=obj.M*obj.R;
           end
           
           M = obj.M;
           
        end
        
         function R=matriz_r(obj,w,dt)
            
            obj.mod_w = sqrt(w(1)^2 + w(2)^2 + w(3)^2); %módulo de w
                
            if(obj.mod_w>0)
                
                obj.ew(1) = w(1)/(obj.mod_w); %ewx
                obj.ew(2) = w(2)/(obj.mod_w); %ewy
                obj.ew(3) = w(3)/(obj.mod_w); %ewz
            
                       
            %Obter theta
    
            obj.theta = obj.mod_w*dt;
            %%%http://www.sciencedirect.com/science/article/pii/004269899400257M
            obj.R(1,1) = cos(obj.theta) + (obj.ew(1)^2)*(1-cos(obj.theta));
            obj.R(1,2) = obj.ew(1)*obj.ew(2)*(1-cos(obj.theta)) - obj.ew(3)*sin(obj.theta);
            obj.R(1,3) = obj.ew(1)*obj.ew(3)*(1-cos(obj.theta)) + obj.ew(2)*sin(obj.theta);

            obj.R(2,1) = obj.ew(2)*obj.ew(1)*(1-cos(obj.theta)) + obj.ew(3)*sin(obj.theta);
            obj.R(2,2) = cos(obj.theta) + (obj.ew(2)^2)*(1-cos(obj.theta));
            obj.R(2,3) = obj.ew(2)*obj.ew(3)*(1-cos(obj.theta)) - obj.ew(1)*sin(obj.theta);

            obj.R(3,1) = obj.ew(3)*obj.ew(1)*(1-cos(obj.theta)) - obj.ew(2)*sin(obj.theta) ;
            obj.R(3,2) = obj.ew(3)*obj.ew(2)*(1-cos(obj.theta)) + obj.ew(1)*sin(obj.theta) ;
            obj.R(3,3) = cos(obj.theta) + (obj.ew(3)^2)*(1-cos(obj.theta));
            
            else
                
                obj.R=eye(3);
                
            end

            R=obj.R;
        end

   end
end
