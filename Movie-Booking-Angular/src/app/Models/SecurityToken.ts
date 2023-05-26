export class SecurityToken{
    public jwt: String ="";
    constructor(){}

    public get Jwt(): String{
        return this.jwt;
    }
    public set Jwt(jwt : String){
        this.jwt=jwt;
    }
}