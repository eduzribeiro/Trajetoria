% Case que tira a media dos L primeiros dados
classdef setup_function_rt_media_l < handle
   properties
        g0
        I
        Si
   end
   methods
        %Construtor inializador do objeto
        % inival: inicia todas as variaveis
        function obj =setup_function_rt_media_l(initval)
        
            obj.g0=[initval initval initval];
            obj.I=initval;
            obj.Si=[initval initval initval];
            
        end
        %ar: input signa  [1x3]
        %return :::
        %g0: A media dos L primeiros [1x3]
        function g0 = media_de_l(obj,ar,L)
            
            if obj.I < L+1
                
                obj.Si = obj.Si + ar;                
                obj.I = obj.I + 1;          
                
            end
            
            if obj.I == L
               
                obj.g0 = (obj.Si)/L;
                
            end
        
            g0 = obj.g0;
            
          end

   end
end