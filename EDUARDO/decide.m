% Classe que decide qual dado utilizar
classdef decide < handle
   properties
        Xd
        aux
        type
   end
   methods
       %Construtor inializador do objeto
       %Type:
       %1:      ruido = 0;
       %2:      gravidade = [0 0 0];
        function obj = decide(type)
            
            obj.aux = 0;
            obj.type = type;
         
            if obj.type == 1
            
                obj.Xd = 0;
            
            else
                
                obj.Xd = [0 0 0];
            end
                       
                       
        end
        
        %x0: input signal  [1x3] ou [1x1]       
        %x0n: input signal  [1x3] ou [1x1]
        %Ready: novo valor de x pronto para ser utilizado [0 (não pronto) ou 1 (pronto)]
        %return :::
        %Xd: X decidido [1x3] ou [1x1]
        
         function Xd = decide_rt(obj,x0,x0n,ready)
            
             if ready == 0 && obj.aux == 0
                 
                 obj.Xd = x0;
                 
             end
                       
            if ready == 1
                                
                obj.Xd = x0n;            
                obj.aux = 1;
                
            end
              
            Xd = obj.Xd;
            
          end

   end
end