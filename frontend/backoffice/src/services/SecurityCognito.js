import { Amplify } from 'aws-amplify';
import '@aws-amplify/ui-react/styles.css';

export default function configureCognito() {
  const cognito = Amplify.configure({
    Auth: {
        region: 'us-east-1',
        userPoolId: 'us-east-1_PJPaKcCHC',
        userPoolWebClientId: '5on5areeae8p62k01rr9p9teup'
    }
  });
}