classdef decide_detector < handle
   properties
    
   DbDecidido
       
   end
   methods
   % Construtor inializador do objeto
   % inival: inicia todas as variaveis
        function obj =decide_detector

            obj.DbDecidido = 0;
            
            
        end
    
	    function DbDecidido = decidido(obj,Db,Db2)
		
			if ((Db == 1) || (Db2==1))
        
				obj.DbDecidido=1;

			else

				obj.DbDecidido=0;
		
			end						  
         
         DbDecidido = obj.DbDecidido;
                
        end                   
        
   end
end
