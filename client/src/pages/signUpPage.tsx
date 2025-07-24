import {
    Card,
    CardAction,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from "@/components/ui/card"
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Button } from "@/components/ui/button";

const SignUpPage: React.FC = () => {
    return (
        <>
            <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100">
                <Card className="w-md">
                    <CardHeader>
                        <CardTitle>Sign Up</CardTitle>
                        <CardDescription>Enter your credentials to sign up</CardDescription>
                        <CardAction></CardAction>
                    </CardHeader>
                    <CardContent>
                        <form className="flex flex-col gap-4" action="" method="">
                            <div className="flex flex-col gap-2">
                                <Label htmlFor="username">Username</Label>
                                <Input
                                    id="username"
                                    name="username"
                                    type="text"
                                    placeholder=""
                                />
                            </div>
                            <div className="flex flex-col gap-2">
                                <Label htmlFor="email">Email</Label>
                                <Input
                                    id="email"
                                    name="email"
                                    type="email"
                                    placeholder=""
                                />
                            </div>
                            <div className="flex flex-col gap-2">
                                <Label htmlFor="password">Password</Label>
                                <Input
                                    id="password"
                                    name="password"
                                    type="password"
                                    placeholder=""
                                />
                            </div>
                            <div className="flex flex-col gap-2">
                                <Label htmlFor="confirmPass">Confirm password</Label>
                                <Input
                                    id="confirmPass"
                                    name="confirmPass"
                                    type="password"
                                    placeholder=""
                                />
                            </div>
                            <Button type="submit" className="mt-2" variant={"default"}>
                                Sign Up
                            </Button>
                        </form>
                    </CardContent>
                    <CardFooter>

                    </CardFooter>
                </Card>
            </div>
        </>
    );
};

export default SignUpPage;